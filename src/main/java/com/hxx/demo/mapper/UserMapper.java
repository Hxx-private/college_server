package com.hxx.demo.mapper;

import com.hxx.demo.entity.Role;
import com.hxx.demo.entity.User;
import org.apache.ibatis.annotations.Param;


import java.util.List;
public interface UserMapper  {
    User loadUserByUsername(@Param("uName") String uName);

    List<Role> getRolesByUserId(Long id);

    int userReg(@Param("userName") String userName, @Param("password") String password);

    List<User> getUsersByKeywords(@Param("keywords") String keywords);

    int updateUser(User user);

    int deleteRoleByUserId(Long userId);

    int addRolesForUser(@Param("userId") Long userId, @Param("rids") Long[] rids);

    User getUserById(Long userId);

    int deleteUser(Long userId);

    List<User> getAllUser(@Param("currentId") Long currentId);

    List<User> findBykeywords(@Param("sql") String sql);
}
