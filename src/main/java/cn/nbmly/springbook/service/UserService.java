package cn.nbmly.springbook.service;

import cn.nbmly.springbook.po.User;

import java.util.List;

public interface UserService {
    boolean isValidatedUser(User user, int roleId);

    User findUserByUserId(int userId);

    List<User> findUsers();

    int addUser(User user);

    int deleteUserByUserId(int userId);

    int updateUser(User user, int[] roleIds);

    int updatePassword(String originPwd, String newPwnd, String loginName);

    boolean registerUser(User user);

    int resetPassword(int userId);
}
