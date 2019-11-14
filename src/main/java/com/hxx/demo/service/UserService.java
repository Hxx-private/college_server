package com.hxx.demo.service;

import com.hxx.demo.dao.UserDao;
import com.hxx.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    /**
     * @Author Hxx
     * @Description //TODO 用户注册
     * @Date 16:41 2019/10/28
     * @Param [user]
     **/
    public void addUser(User user) {
        userDao.addUser(user);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 用户登录
     * @Date 15:47 2019/10/28
     * @Param [username, password]
     **/
    public User login(String userName, String password) {
        return userDao.login(userName, password);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据用户名查找用户
     * @Date 17:05 2019/10/28
     * @Param [userName]
     **/
    public User findByUsername(String userName) {
        return userDao.findByUserName(userName);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据学号查找用户
     * @Date 17:05 2019/10/28
     * @Param [userName]
     **/
    public List<Map<String, Object>> findByNumber(String number) {
        return userDao.findByUserNumber(number);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 修改密码
     * @Date 10:05 2019/10/29
     * @Param [password]
     **/
    public void changePwd(User user) {
        userDao.changePwd(user);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据真实姓名查找用户
     * @Date 17:05 2019/10/28
     * @Param [userName]
     **/
    public List<Map<String, Object>> findByName(String name) {
        return userDao.findByName(name);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 用户修改资料
     * @Date 16:34 2019/10/30
     * @Param [user]
     **/
    public void alterUser(User user) {
        userDao.alterUser(user);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 显示个人信息
     * @Date 10:04 2019/10/31
     * @Param [userName]
     **/
    public User showSelfInfo(String userName) {

        return userDao.showSelfInfo(userName);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.User>
     * @Author Hxx
     * @Description //TODO 查询所有用户
     * @Date 11:03 2019/11/7
     * @Param []
     **/
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加维修人员或管理员
     * @Date 11:05 2019/11/7
     * @Param [user]
     **/
    public void createUser(User user) {
        userDao.createUser(user);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据用户名删除用户
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delByUserName(String userName) {
        userDao.delByUserName(userName);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据学号或工号删除用户信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delByNumber(String number) {
        userDao.delByNumber(number);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }
}
