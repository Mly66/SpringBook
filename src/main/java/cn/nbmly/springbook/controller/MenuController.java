package cn.nbmly.springbook.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.nbmly.springbook.mapper.*;
import cn.nbmly.springbook.po.*;
import cn.nbmly.springbook.service.UserService;
import cn.nbmly.springbook.tools.Result;
import cn.nbmly.springbook.tools.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private PublisherMapper publisherMapper;
    @Autowired
    private ApplyBookMapper applyBookMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DistributeBookMapper distributeBookMapper;

    /**
     * 获取用户菜单数据
     * 
     * @param menuType 菜单类型
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param session  会话
     * @return 菜单数据
     */
    @GetMapping("/data")
    public Result<Object> getMenuData(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam int menuType,
            HttpSession session) {

        String loginName = (String) session.getAttribute("loginName");
        if (loginName == null) {
            return Result.unauthorized();
        }

        PageHelper.startPage(pageNum, pageSize);

        switch (menuType) {
            case 1: // 用户数据
                List<User> userList = userService.findUsers();
                PageInfo<User> pageInfo = new PageInfo<>(userList);
                return Result.success(pageInfo);

            case 2: // 课程管理
                List<Course> courseList = courseMapper.findCourses();
                PageInfo<Course> pageInfoCourse = new PageInfo<>(courseList);
                return Result.success(pageInfoCourse);

            case 3: // 审批管理
                List<ApplyBook> applyBookList1 = applyBookMapper.findNoApprovedApplyBooks();
                for (ApplyBook applyBook : applyBookList1) {
                    applyBook.setApplyStatusText(SysConstant.APPLY_STATUS[applyBook.getApplyStatus()]);
                }
                PageInfo<ApplyBook> pageInfoApplyBook1 = new PageInfo<>(applyBookList1);
                return Result.success(pageInfoApplyBook1);

            case 4: // 入库管理
                List<ApplyBook> applyBookList2 = applyBookMapper.findApprovedApplyBooks();
                for (ApplyBook applyBook : applyBookList2) {
                    applyBook.setApplyStatusText(SysConstant.APPLY_STATUS[applyBook.getApplyStatus()]);
                }
                PageInfo<ApplyBook> pageInfoApplyBook2 = new PageInfo<>(applyBookList2);
                return Result.success(pageInfoApplyBook2);

            case 5: // 教材发放
                List<ApplyBook> applyBookList3 = applyBookMapper.findApplyBooksInStorage();
                for (ApplyBook applyBook : applyBookList3) {
                    applyBook.setApplyStatusText(SysConstant.APPLY_STATUS[applyBook.getApplyStatus()]);
                }
                PageInfo<ApplyBook> pageInfoApplyBook3 = new PageInfo<>(applyBookList3);
                return Result.success(pageInfoApplyBook3);

            case 6: // 教材申请
                Map<String, Object> result = new HashMap<>();

                List<Book> bookList = bookMapper.findBooks();
                PageInfo<Book> pageInfoBook = new PageInfo<>(bookList);
                result.put("books", pageInfoBook);

                List<Publisher> publisherList1 = publisherMapper.findPublishers();
                result.put("publishers", publisherList1);

                List<Course> courseList1 = courseMapper.findCourses();
                result.put("courses", courseList1);

                return Result.success(result);

            case 7: // 查看申请
                int userId = userMapper.findUserIdByLoginName(loginName);
                List<ApplyBook> applyBookList = applyBookMapper.findApplyBooksByUserId(userId);
                for (ApplyBook applyBook : applyBookList) {
                    applyBook.setApplyStatusText(SysConstant.APPLY_STATUS[applyBook.getApplyStatus()]);
                }
                PageInfo<ApplyBook> pageInfoApplyBook = new PageInfo<>(applyBookList);
                return Result.success(pageInfoApplyBook);

            case 8: // 出版社管理
                List<Publisher> publisherList = publisherMapper.findPublishers();
                PageInfo<Publisher> pageInfoPublisher = new PageInfo<>(publisherList);
                return Result.success(pageInfoPublisher);

            case 9: // 修改密码
                return Result.success(null);

            case 10: // 发放历史查询
                List<DistributeBook> distributeBookList = distributeBookMapper.findDistributeBooks();
                PageInfo<DistributeBook> pageInfoDistributeBook = new PageInfo<>(distributeBookList);
                return Result.success(pageInfoDistributeBook);

            default:
                return Result.failed("未知的菜单类型");
        }
    }
}
