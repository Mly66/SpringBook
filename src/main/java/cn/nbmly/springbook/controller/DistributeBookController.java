package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.mapper.ApplyBookMapper;
import cn.nbmly.springbook.mapper.DistributeBookMapper;
import cn.nbmly.springbook.mapper.UserMapper;
import cn.nbmly.springbook.po.ApplyBook;
import cn.nbmly.springbook.po.Book;
import cn.nbmly.springbook.po.DistributeBook;
import cn.nbmly.springbook.po.User;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distribute-book")
public class DistributeBookController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DistributeBookMapper distributeBookMapper;
    @Autowired
    private ApplyBookMapper applyBookMapper;

    /**
     * 发放教材
     */
    @PostMapping("/distribute")
    public Result<String> distributeBook(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 提取distributeCount
        Integer distributeCount = null;
        if (requestMap.containsKey("distributeCount")) {
            distributeCount = Integer.parseInt(requestMap.get("distributeCount").toString());
        }

        // 提取applyBook信息
        Integer bookId = null;
        Integer applicantUserId = null;

        if (requestMap.containsKey("applyBook")) {
            Map<String, Object> applyBookMap = (Map<String, Object>) requestMap.get("applyBook");

            // 尝试直接获取book_id
            if (applyBookMap.containsKey("book") && applyBookMap.get("book") instanceof Map) {
                Map<String, Object> bookMap = (Map<String, Object>) applyBookMap.get("book");
                if (bookMap.containsKey("bookId")) {
                    bookId = Integer.parseInt(bookMap.get("bookId").toString());
                }
            }

            // 尝试直接获取user_id
            if (applyBookMap.containsKey("user") && applyBookMap.get("user") instanceof Map) {
                Map<String, Object> userMap = (Map<String, Object>) applyBookMap.get("user");
                if (userMap.containsKey("userId")) {
                    applicantUserId = Integer.parseInt(userMap.get("userId").toString());
                }
            }
        }

        // 检查必要参数
        if (bookId == null || applicantUserId == null || distributeCount == null) {
            return Result.failed("缺少必要参数：图书ID、申请用户ID或发放数量");
        }

        // 创建DistributeBook对象
        DistributeBook distributeBook = new DistributeBook();

        // 设置图书信息
        Book book = new Book();
        book.setBookId(bookId);
        distributeBook.setBook(book);

        // 设置申请人信息 - 这里使用的是申请人的ID，不是当前登录用户的ID
        User applicantUser = new User();
        applicantUser.setUserId(applicantUserId);
        distributeBook.setUser(applicantUser);

        // 设置发放数量
        distributeBook.setDistributeCount(distributeCount);

        // 设置发放日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        distributeBook.setDistributeDate(simpleDateFormat.format(Calendar.getInstance().getTime()));

        // 检查是否已存在该图书的发放记录
        DistributeBook existingRecord = distributeBookMapper.findDistributeBookByBookIdAndUserId(bookId,
                applicantUserId);

        int result;
        if (existingRecord != null) {
            // 如果已存在，更新记录
            result = distributeBookMapper.updateDistributeBook(distributeBook);
        } else {
            // 如果不存在，添加新记录
            result = distributeBookMapper.addDistributeBook(distributeBook);
        }

        return result > 0 ? Result.success("发放成功") : Result.failed("发放失败");
    }

    /**
     * 获取发放历史
     */
    @GetMapping("/history")
    public Result<List<DistributeBook>> getDistributeHistory() {
        List<DistributeBook> distributeBooks = distributeBookMapper.findDistributeBooks();
        return Result.success(distributeBooks);
    }

    /**
     * 从申请记录发放教材
     * 请求格式更简单：{bookId: 1, userId: 1, distributeCount: 10}
     */
    @PostMapping("/distribute-from-apply")
    public Result<String> distributeFromApply(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 提取参数
        Integer bookId = null;
        Integer userId = null;
        Integer distributeCount = null;

        if (requestMap.containsKey("bookId")) {
            bookId = Integer.parseInt(requestMap.get("bookId").toString());
        }

        if (requestMap.containsKey("userId")) {
            userId = Integer.parseInt(requestMap.get("userId").toString());
        }

        if (requestMap.containsKey("distributeCount")) {
            distributeCount = Integer.parseInt(requestMap.get("distributeCount").toString());
        }

        // 检查必要参数
        if (bookId == null || userId == null || distributeCount == null) {
            return Result.failed("缺少必要参数：图书ID、用户ID或发放数量");
        }

        // 创建DistributeBook对象
        DistributeBook distributeBook = new DistributeBook();

        // 设置图书信息
        Book book = new Book();
        book.setBookId(bookId);
        distributeBook.setBook(book);

        // 设置用户信息
        User user = new User();
        user.setUserId(userId);
        distributeBook.setUser(user);

        // 设置发放数量
        distributeBook.setDistributeCount(distributeCount);

        // 设置发放日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        distributeBook.setDistributeDate(simpleDateFormat.format(Calendar.getInstance().getTime()));

        // 检查是否已存在该图书的发放记录
        DistributeBook existingRecord = distributeBookMapper.findDistributeBookByBookIdAndUserId(bookId, userId);

        int result;
        if (existingRecord != null) {
            // 如果已存在，更新记录
            result = distributeBookMapper.updateDistributeBook(distributeBook);
        } else {
            // 如果不存在，添加新记录
            result = distributeBookMapper.addDistributeBook(distributeBook);
        }

        return result > 0 ? Result.success("发放成功") : Result.failed("发放失败");
    }
}
