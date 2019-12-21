package com.hxx.demo.dao;

import com.hxx.demo.entity.Role;
import com.hxx.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 用户注册
     * @Date 15:20 2019/10/28
     * @Param [user]
     **/
    @Insert(" INSERT INTO user set userName=#{userName},password=#{password}")
    int userReg(@Param("userName") String userName, @Param("password") String password);

    //void updateActiveId(Map<String, Object> updateIdMap);

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 用户登录
     * @Date 15:18 2019/10/28
     * @Param [userName, password]
     **/
    @Select("SELECT * FROM user WHERE userName = #{userName} AND password = #{password}")
    User login(@Param("userName") String userName, @Param("password") String password);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 修改密码
     * @Date 9:47 2019/10/29
     * @Param [user]
     **/

    @Update("UPDATE user SET password=#{user.pwdNew} WHERE userName=#{user.userName}")
    void changePwd(@Param("user") User user);

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据用户名查找用户
     * @Date 15:10 2019/10/28
     * @Param [userName]
     **/
    @Select("SELECT * FROM user WHERE userName =#{userName} AND id <> #{id}")
    User findByUserName(@Param("userName") String userName, @Param("id") long id);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 修改资料
     * @Date 9:41 2019/10/29
     * @Param [user]
     **/
    @Update("UPDATE user SET userName=#{user.userName},sex=#{user.sex},age=#{user.age},tel=#{user.tel} WHERE userName=#{user.userName}")
    int alterUser(@Param("user") User user);


    /**
     * @return java.util.List<com.hxx.demo.entity.User>
     * @Author Hxx
     * @Description //TODO 查询所有用户信息
     * @Date 9:52 2019/10/29
     * @Param []
     **/
    @Select("SELECT * FROM user where userName<> 'sysadmin'")
    List<User> findAll();


    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据学号查找用户
     * @Date 15:10 2019/10/28
     * @Param [userName]
     **/
    @Select("SELECT * FROM user WHERE number = #{number} ")
    List<Map<String, Object>> findByUserNumber(@Param("number") String number);

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据真实姓名查找用户
     * @Date 15:10 2019/10/28
     * @Param [userName]
     **/
    @Select("SELECT * FROM user WHERE name=#{name}")
    List<User> findByName(@Param("name") String name);

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 显示个人信息
     * @Date 10:02 2019/10/31
     * @Param [userName]
     **/
    @Select("SELECT userName,sex,age,number,depart,special,tel,registerTime FROM user WHERE userName  =#{userName}")
    User showSelfInfo(@Param("userName") String userName);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加维修人员和管理员
     * @Date 11:01 2019/11/7
     * @Param [user]
     **/
    @Insert("INSERT INTO user (userName,name,password,sex,number,tel,registerTime,enabled) VALUES (#{user.userName},#{user.name},#{user.password},#{user.sex},#{user.number},#{user.tel},#{user.enabled},#{user.registerTime})")
    int createUser(@Param("user") User user);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据用户名删除用户
     * @Date 14:56 2019/11/7
     * @Param [user]
     **/
    @Delete("DELETE FROM user WHERE userName =#{userName}")
    int delByUserName(@Param("userName") String userName);


    @Select("SELECT r.* FROM user_role ur,role r where ur.rid=r.id AND ur.userid=#{id}")
    List<Role> getRolesByUserId(Long id);

    @Select("SELECT * FROM user WHERE userName=#{userName}")
    @Results({
            @Result(id = true, column = "id", property = "id"),//获取自增id  (必须设置id为true)
            @Result(property = "roles", column = "id", many = @Many(select = "com.hxx.demo.dao.UserDao.getRolesByUserId"))
    })
    User loadUserByUsername(@Param("userName") String userName);


    @Delete("")
    int deleteBatch();

    @Select("SELECT COUNT(1) FROM user WHERE userName <> 'sysadmin'")
    int total();
}
