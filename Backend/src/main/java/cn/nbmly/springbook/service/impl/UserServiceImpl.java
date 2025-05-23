package cn.nbmly.springbook.service.impl;

import cn.nbmly.springbook.mapper.UserMapper;
import cn.nbmly.springbook.mapper.UserRoleMapper;
import cn.nbmly.springbook.po.Role;
import cn.nbmly.springbook.po.User;
import cn.nbmly.springbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean isValidatedUser(User user, int roleId) {
        User dbUser = userMapper.findUserByLoginName(user.getLoginName());
        if (dbUser == null)
            return false;
        if (dbUser.getPassword().equals(user.getPassword()) && isValidateRole(roleId, dbUser.getRoleList()))
            return true;
        return false;
    }

    protected boolean isValidateRole(int roleId, List<Role> roleList) {
        for (int i = 0; i < roleList.size(); i++) {
            if (roleList.get(i).getRoleId() == roleId)
                return true;
        }
        return false;
    }

    @Override
    public User findUserByUserId(int userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public List<User> findUsers() {
        return userMapper.findUsers();
    }

    @Override
    public int addUser(User user) {
        int res = userMapper.addUser(user);
        int res1 = userRoleMapper.addUserRole(user.getUserId(), 3);
        return res;
    }

    @Override
    public int deleteUserByUserId(int userId) {
        // System.out.println(userId);
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserByUserId(userId);
    }

    @Override
    public int updateUser(User user, int[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(user.getUserId());
        for (int i = 0; i < roleIds.length; i++)
            userRoleMapper.addUserRole(user.getUserId(), roleIds[i]);
        return userMapper.updateUser(user);
    }

    @Override
    public int updatePassword(String originPwd, String newPwd, String loginName) {
        User user = userMapper.findUserByLoginName(loginName);
        if (originPwd.compareTo(user.getPassword()) != 0)
            return 0;
        user.setPassword(newPwd);
        return userMapper.updatePassword(user);
    }

    @Override
    public boolean registerUser(User user) {
        User dbUser = userMapper.findUserByLoginName(user.getLoginName());
        if (dbUser != null) // 用户已存在
            return false;
        int res = userMapper.addUser(user);
        int res1 = userRoleMapper.addUserRole(user.getUserId(), 3);// 默认新注册用户为学生用户
        return res == 1 && res1 == 1 ? true : false;
    }

    @Override
    public int resetPassword(int userId) {
        return userMapper.resetPassword(userId);
    }
}
