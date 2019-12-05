package com.hxx.demo.service;

import com.hxx.demo.dao.UserDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.User;
import com.hxx.demo.utils.ParamsInitUtils;
import com.hxx.demo.mapper.UserMapper;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

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
    public List<User> findByName(String name) {
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
     * @Description //TODO 根据id删除用户
     * @Date 15:03 2019/11/7
     * @Param [id]
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
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }

/**
 * @Author Hxx
 * @Description //TODO 用户注册
 * @Date 11:26 2019/11/20
 * @Param [userName, password]
 * @return int
 **/
    public int userReg(String userName, String password) {
        //如果用户名存在，返回错误
        if (userDao.loadUserByUsername(userName) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return userDao.userReg(userName, encode);
    }

    public List<User> getAllUserExceptAdmin() {
        return userMapper.getAllUser(UserUtils.getCurrentUser().getId());
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser(null);
    }


    public List<User> getUsersByKeywords(String keywords) {
        return userMapper.getUsersByKeywords(keywords);
    }


    public int updateUserRoles(Long userId, Long[] rids) {
        int i = userMapper.deleteRoleByUserId(userId);
        return userMapper.addRolesForUser(userId, rids);
    }

    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    public int deleteUser(Long userId) {
        return userMapper.deleteUser(userId);
    }
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int total(){
        return userDao.total();
    }

    public List<User> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "user");
        List<User> users = this.userMapper.findBykeywords(sql);
        return users;
    }
}
