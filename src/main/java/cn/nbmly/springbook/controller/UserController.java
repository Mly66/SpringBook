package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.po.User;
import cn.nbmly.springbook.service.UserService;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, Object> loginInfo, HttpSession session) {
        User user = new User();
        user.setLoginName((String) loginInfo.get("loginName"));
        user.setPassword((String) loginInfo.get("password"));
        int roleId = Integer.parseInt(loginInfo.get("roleId").toString());

        if (!userService.isValidatedUser(user, roleId)) {
            return Result.failed("用户名或密码错误");
        }

        session.setAttribute("loginName", user.getLoginName());
        session.setAttribute("roleId", roleId);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("loginName", user.getLoginName());
        userInfo.put("roleId", roleId);

        return Result.success(userInfo, "登录成功");
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        boolean res = userService.registerUser(user);
        return res ? Result.success("注册成功") : Result.failed("注册失败");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userId}")
    public Result<String> deleteUser(@PathVariable int userId) {
        int res = userService.deleteUserByUserId(userId);
        return res > 0 ? Result.success("删除成功") : Result.failed("删除失败");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{userId}")
    public Result<User> getUser(@PathVariable int userId) {
        User user = userService.findUserByUserId(userId);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.failed("用户不存在");
        }
    }

    /**
     * 重置密码
     */
    @PutMapping("/{userId}/reset-password")
    public Result<String> resetPassword(@PathVariable int userId) {
        int res = userService.resetPassword(userId);
        return res > 0 ? Result.success("密码重置成功") : Result.failed("密码重置失败");
    }

    /**
     * 保存用户信息（新增或更新）
     */
    @PostMapping("/save")
    public Result<String> saveUser(@RequestBody User user, @RequestParam int[] roleIds) {
        if (user.getUserId() > 0) { // 编辑
            userService.updateUser(user, roleIds);
            return Result.success("用户更新成功");
        } else { // 添加
            // 新用户密码固定为888888
            user.setPassword("888888");
            userService.addUser(user);
            return Result.success("用户添加成功");
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/change-password")
    public Result<String> modifyPassword(@RequestBody Map<String, String> passwordInfo) {
        String originPwd = passwordInfo.get("originPwd");
        String newPwd = passwordInfo.get("newPwd");
        String loginName = passwordInfo.get("loginName");

        int ret = userService.updatePassword(originPwd, newPwd, loginName);
        return ret > 0 ? Result.success("密码修改成功") : Result.failed("原密码错误或修改失败");
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    public Result<Map<String, Object>> getCurrentUser(HttpSession session) {
        String loginName = (String) session.getAttribute("loginName");
        Integer roleId = (Integer) session.getAttribute("roleId");

        if (loginName == null) {
            return Result.unauthorized();
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("loginName", loginName);
        userInfo.put("roleId", roleId);

        return Result.success(userInfo);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.invalidate();
        return Result.success("登出成功");
    }
}