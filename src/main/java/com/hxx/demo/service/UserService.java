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
     * @Param [uName]
     **/
    public User findByUsername(String uName, long id) {
        return userDao.findByUserName(uName, id);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据学号查找用户
     * @Date 17:05 2019/10/28
     * @Param [uName]
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
     * @Param [uName]
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
    public int alterUser(User user) {
        return userDao.alterUser(user);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 显示个人信息
     * @Date 10:04 2019/10/31
     * @Param [uName]
     **/
    public User showSelfInfo(String uName) {

        return userDao.showSelfInfo(uName);
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
    public int createUser(User user) {
        return userDao.createUser(user);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据用户名删除
     * @Date 15:03 2019/11/7
     * @Param [id]
     **/
    public int delByUserName(String uName) {
        return userDao.delByUserName(uName);
    }


    @Override
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(uName);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return user;
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 用户注册
     * @Date 11:26 2019/11/20
     * @Param [userName, password]
     **/
    public int userReg(String uName, String password) {
        //如果用户名存在，返回错误
        if (userDao.findByUName(uName) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return userDao.userReg(uName, encode);
    }

    public int Reg(long id) {
        int i = userDao.Reg(id);
        if (i > 0) {
            return userDao.Reg(id);
        }
        return -1;
    }

    public List<User> getAllUserExceptAdmin() {
        return userMapper.getAllUser(UserUtils.getCurrentUser().getId());
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser(null);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.User>
     * @Author Hxx
     * @Description //TODO 搜索操作员
     * @Date 15:55 2019/12/27
     * @Param [keywords]
     **/
    public List<User> getUsersByKeywords(String keywords) {
        return userMapper.getUsersByKeywords(keywords);
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 分配权限
     * @Date 15:54 2019/12/27
     * @Param [userId, rids]
     **/
    public int updateUserRoles(Long userId, Long[] rids) {
        int i = userMapper.deleteRoleByUserId(userId);
        if (i > 0) {
            return userMapper.addRolesForUser(userId, rids);
        }
        return  -1;
    }

    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    public int deleteUser(Long userId) {
        return userMapper.deleteUser(userId);
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 用户修改资料
     * @Date 15:54 2019/12/27
     * @Param [user]
     **/
    public int updateSelf(User user) {
        return userDao.updateUser(user);
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 更新用户
     * @Date 15:53 2019/12/27
     * @Param [user]
     **/
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.User>
     * @Author Hxx
     * @Description //TODO 根据指定字段查找用户
     * @Date 15:53 2019/12/27
     * @Param [gridJson]
     **/
    public List<User> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "user");
        List<User> users = this.userMapper.findBykeywords(sql);
        return users;
    }

    public int total() {
        return userDao.total();
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据用户名查找用户  不显示系统管理员
     * @Date 15:53 2019/12/27
     * @Param [uName, id]
     **/
    public User findByUserName(String uName, long id) {
        return userDao.findByUserName(uName, id);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据用户名查找用户
     * @Date 15:52 2019/12/27
     * @Param [uName]
     **/
    public User findByUName(String uName) {
        return userDao.findByUName(uName);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据id查找用户
     * @Date 15:51 2019/12/27
     * @Param [id]
     **/
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 用户编辑时判断用户名唯一
     * @Date 15:51 2019/12/27
     * @Param [uName, id]
     **/
    public User findindByUserNameNotSelf(String uName, long id) {
        return userDao.findindByUserNameNotSelf(uName, id);
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 15:50 2019/12/27
     * @Param [ids]
     **/
    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        return userDao.deleteBatch(split) == split.length;
    }
}
