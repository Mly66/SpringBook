package cn.nbmly.springbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    int addUserRole(@Param("userId") int userId, @Param("roleId") int roleId);

    int deleteUserRoleByUserId(int userId);
}
