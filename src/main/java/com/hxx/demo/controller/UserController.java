package com.hxx.demo.controller;


import com.hxx.demo.dao.UserDao;
import com.hxx.demo.entity.RespBean;
import com.hxx.demo.entity.Role;
import com.hxx.demo.entity.User;
import com.hxx.demo.service.RoleService;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hxx
 */
@Api(value = "UserController")
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 个人中心
     * @Date 10:12 2019/10/31
     * @Param [uName]
     **/
    @GetMapping(value = "/show")
    public User showSelfInfo() {
        return UserUtils.getCurrentUser();
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 修改资料
     * @Date 16:49 2019/10/30
     * @Param [user]
     **/
    @PostMapping(value = "/alter")
    public RespBean alterUser(@RequestBody User user) {
        if (null != userService.findByUserName(user.getUsername(), user.getId())) {
            int i = userService.alterUser(user);
            if (i > 0) {
                return RespBean.ok("修改成功");
            }
            return RespBean.error("修改失败");
        }

        return RespBean.error("用户名存在");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 用户注册
     * @Date 14:13 2019/11/19
     * @Param [username, password]
     **/
    @PostMapping(value = "/reg")
    public RespBean userReg(String uName, String password) {
        int i = userService.userReg(uName, password);
        long id = userService.findByUName(uName).getId();
        int j = userService.Reg(id);
        if (i == 1 && j == 1) {
            return RespBean.ok("注册成功!");
        } else if (i == -1) {
            return RespBean.error("用户名重复，注册失败!");
        }
        return RespBean.error("注册失败!");
    }


    @GetMapping(value = "/roles")
    public List<Role> allRoles() {
        return roleService.roles();
    }

    @RequestMapping("/{keywords}")
    public List<User> getUsersByKeywords(@PathVariable(required = false) String keywords) {
        List<User> hrs = userService.getUsersByKeywords(keywords);
        return hrs;
    }

    @RequestMapping("/id/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping(value = "/{userId}")
    public RespBean deleteUser(@PathVariable Long userId) {
        if (userService.deleteUser(userId) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @PutMapping(value = "/")
    public RespBean updateUser(User user) {

        if (userService.updateUser(user) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public RespBean updateHrRoles(Long userId, Long[] rids) {
        if (userService.updateUserRoles(userId, rids) == rids.length) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("/changePwd")
    public RespBean changePassword(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(user.getPassword());
        if (password.equals(userService.findByUName(user.getUsername()).getPassword())) {
            BCryptPasswordEncoder encoders = new BCryptPasswordEncoder();
            String pwd = encoders.encode(user.getPwdNew());
            User user1 = new User();
            user1.setPwdNew(pwd);
            user1.setUName(user.getUsername());
            userService.changePwd(user1);
        } else {
            return RespBean.error("旧密码错误,请重新输入");
        }
       return RespBean.ok("密码修改成功");
    }
}
