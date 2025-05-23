package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.mapper.ApplyBookMapper;
import cn.nbmly.springbook.mapper.UserMapper;
import cn.nbmly.springbook.po.ApplyBook;
import cn.nbmly.springbook.po.User;
import cn.nbmly.springbook.po.Book;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/**
 * 申请图书控制器
 * 注意：apply_book表使用book_id和user_id作为联合主键
 */
@RestController
@RequestMapping("/api/apply-book")
public class ApplyBookController {
    @Autowired
    private ApplyBookMapper applyBookMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取申请详情
     * 
     * @param bookId  图书ID
     * @param session 会话，用于获取当前登录用户ID
     * @return 申请详情
     */
    @GetMapping("/{bookId}")
    public Result<ApplyBook> getApplyBook(@PathVariable(required = false) String bookId, HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 检查bookId是否为undefined或无效值
        if (bookId == null || "undefined".equals(bookId)) {
            return Result.failed("无效的图书ID");
        }

        int bookIdInt;
        try {
            bookIdInt = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            return Result.failed("图书ID必须是数字");
        }

        int userId = userMapper.findUserIdByLoginName(loginName);
        ApplyBook applyBook = applyBookMapper.findApplyBookByBookIdAndUserId(bookIdInt, userId);

        if (applyBook != null) {
            return Result.success(applyBook);
        } else {
            return Result.failed("未找到申请记录");
        }
    }

    /**
     * 修改申请数量
     * 
     * @param bookId    图书ID
     * @param applyBook 申请信息
     * @param session   会话，用于获取当前登录用户ID
     * @return 修改结果
     */
    @PutMapping("/{bookId}/count")
    public Result<String> editApplyCount(@PathVariable(required = false) String bookId,
            @RequestBody ApplyBook applyBook,
            HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 检查bookId是否为undefined或无效值
        if (bookId == null || "undefined".equals(bookId)) {
            return Result.failed("无效的图书ID");
        }

        int bookIdInt;
        try {
            bookIdInt = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            return Result.failed("图书ID必须是数字");
        }

        // 设置book_id
        if (applyBook.getBook() == null) {
            applyBook.setBook(new Book());
        }
        applyBook.getBook().setBookId(bookIdInt);

        // 设置user_id
        if (applyBook.getUser() == null) {
            applyBook.setUser(new User());
        }
        int userId = userMapper.findUserIdByLoginName(loginName);
        applyBook.getUser().setUserId(userId);

        int result = applyBookMapper.editApplyCount(applyBook);
        return result > 0 ? Result.success("修改成功") : Result.failed("修改失败");
    }

    /**
     * 审批申请
     * 
     * @param bookId    图书ID
     * @param applyBook 申请信息，包含applyStatus和可选的user信息
     * @param session   会话，用于获取当前审批人ID
     * @return 审批结果
     */
    @PutMapping("/{bookId}/approve")
    public Result<String> approveApply(@PathVariable(required = false) String bookId, @RequestBody ApplyBook applyBook,
            HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 检查bookId是否为undefined或无效值
        if (bookId == null || "undefined".equals(bookId)) {
            return Result.failed("无效的图书ID");
        }

        int bookIdInt;
        try {
            bookIdInt = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            return Result.failed("图书ID必须是数字");
        }

        // 获取当前审批人ID
        int approverUserId = userMapper.findUserIdByLoginName(loginName);

        ApplyBook existingApply = null;

        // 如果指定了用户，直接查询该用户的申请
        if (applyBook.getUser() != null && applyBook.getUser().getUserId() > 0) {
            existingApply = applyBookMapper.findApplyBookByBookIdAndUserId(bookIdInt, applyBook.getUser().getUserId());
        } else {
            // 如果没有指定用户，尝试获取该书的第一条未审批记录
            List<ApplyBook> pendingApplies = applyBookMapper.findNoApprovedApplyBooksByBookId(bookIdInt);
            if (pendingApplies != null && !pendingApplies.isEmpty()) {
                existingApply = pendingApplies.get(0);
            }
        }

        // 如果找不到申请记录
        if (existingApply == null) {
            return Result.failed("未找到待审批的申请记录");
        }

        // 设置审批信息
        existingApply.setApplyStatus(applyBook.getApplyStatus());
        existingApply.setApprover(approverUserId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        existingApply.setApproveDate(simpleDateFormat.format(Calendar.getInstance().getTime()));

        int result = applyBookMapper.approveApply(existingApply);
        return result > 0 ? Result.success("审批成功") : Result.failed("审批失败");
    }

    /**
     * 入库处理
     * 
     * @param bookId  图书ID
     * @param session 会话，用于获取当前用户ID
     * @return 处理结果
     */
    @PutMapping("/{bookId}/in-storage")
    public Result<String> inStorageApply(@PathVariable(required = false) String bookId, HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 检查bookId是否为undefined或无效值
        if (bookId == null || "undefined".equals(bookId)) {
            return Result.failed("无效的图书ID");
        }

        int bookIdInt;
        try {
            bookIdInt = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            return Result.failed("图书ID必须是数字");
        }

        int userId = userMapper.findUserIdByLoginName(loginName);

        ApplyBook applyBook = new ApplyBook();
        applyBook.setApplyStatus(3);

        // 设置book和user
        applyBook.setBook(new Book());
        applyBook.getBook().setBookId(bookIdInt);

        applyBook.setUser(new User());
        applyBook.getUser().setUserId(userId);

        int result = applyBookMapper.editApplyBookStatus(applyBook);
        return result > 0 ? Result.success("入库成功") : Result.failed("入库失败");
    }

    /**
     * 获取用户申请的图书列表
     * 
     * @param session 会话，用于获取当前登录用户ID
     * @return 申请图书列表
     */
    @GetMapping("/user-applications")
    public Result<List<ApplyBook>> getUserApplications(HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        int userId = userMapper.findUserIdByLoginName(loginName);
        List<ApplyBook> applyBooks = applyBookMapper.findApplyBooksByUserId(userId);

        if (applyBooks != null && !applyBooks.isEmpty()) {
            return Result.success(applyBooks);
        } else {
            return Result.success(new ArrayList<>()); // 返回空列表而不是失败
        }
    }
}
