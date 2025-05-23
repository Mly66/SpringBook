package cn.nbmly.springbook.controller;

import com.github.pagehelper.PageInfo;
import cn.nbmly.springbook.mapper.ApplyBookMapper;
import cn.nbmly.springbook.mapper.BookMapper;
import cn.nbmly.springbook.mapper.UserMapper;
import cn.nbmly.springbook.po.ApplyBook;
import cn.nbmly.springbook.po.Book;
import cn.nbmly.springbook.po.User;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ApplyBookMapper applyBookMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取图书列表（分页）
     */
    @GetMapping("/list")
    public Result<PageInfo<Book>> getBookList(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // 这里假设已存在getBookList方法，若无此方法需要在BookMapper中添加
        List<Book> books = bookMapper.getBookList(page, size);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        return Result.success(pageInfo);
    }

    /**
     * 保存图书信息（新增或更新）
     */
    @PostMapping("/save")
    public Result<String> saveBook(@RequestBody Book book) {
        int result;
        if (book.getBookId() > 0) {
            result = bookMapper.editBook(book);
        } else {
            result = bookMapper.addBook(book);
        }
        return result > 0 ? Result.success("保存成功") : Result.failed("保存失败");
    }

    /**
     * 获取图书详情
     */
    @GetMapping("/{bookId}")
    public Result<Book> getBook(@PathVariable(required = false) String bookId) {
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

        Book book = bookMapper.findBookByBookId(bookIdInt);
        if (book != null) {
            return Result.success(book);
        } else {
            return Result.failed("未找到该图书");
        }
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{bookId}")
    public Result<String> deleteBook(@PathVariable(required = false) String bookId) {
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

        int count = applyBookMapper.findApplyCountByBookId(bookIdInt);
        if (count > 0) {
            return Result.failed("该图书有申请记录，不能删除");
        }
        int result = bookMapper.deleteBook(bookIdInt);
        return result > 0 ? Result.success("删除成功") : Result.failed("删除失败");
    }

    /**
     * 申请图书
     */
    @PostMapping("/apply")
    public Result<String> applyBook(@RequestBody ApplyBook applyBook, HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        // 设置申请日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        applyBook.setApplyDate(simpleDateFormat.format(Calendar.getInstance().getTime()));

        // 设置用户信息
        applyBook.setUser(new User());
        int userId = userMapper.findUserIdByLoginName(loginName);
        applyBook.getUser().setUserId(userId);

        // 检查该用户是否已经申请过这本书
        ApplyBook existingApply = applyBookMapper.findApplyBookByBookIdAndUserId(
                applyBook.getBook().getBookId(), userId);

        int result;
        if (existingApply != null) {
            // 如果已经申请过，更新申请数量
            result = applyBookMapper.updateApplyCount(applyBook);
        } else {
            // 如果没有申请过，添加新申请
            result = applyBookMapper.addApplyBook(applyBook);
        }

        return result > 0 ? Result.success("申请成功") : Result.failed("申请失败");
    }
}
