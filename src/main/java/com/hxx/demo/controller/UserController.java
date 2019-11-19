package com.hxx.demo.controller;

import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.User;
import com.hxx.demo.service.TokenService;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.AESUtils;
import com.hxx.demo.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
 * @author Hxx
 */
@Api(value = "UserController")
@RestController
public class UserController {
    private static final String KEY = "zhonghuan13xxxxx";
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 用户注册
     * @Date 16:09 2019/10/28
     * @Param [requestUser]
     **/
    @ApiOperation(value = "用户注册", notes = "根据User对象注册用户,必传参数username,password,学生和维修人员需要注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "用户类型", required = true, dataType = "String")

    })
    @PostMapping(value = "/user/register")
    public Map<String, Object> register(@RequestBody User user) throws Exception {
        //获取用户名
        user.setUserName(user.getUsername());
        //对密码进行加密传输
        user.setPassword(AESUtils.AESEncrypt(user.getPassword(), KEY));
        //获取注册时间
        user.setRegisterTime(DateUtils.getSysTime());
        //获取用户类型
        user.setType(user.getType());
        //用户名唯一
        if (userService.findByUsername(user.getUsername()) != null) {
            return Result.failMap("用户名已经存在,请重新输入");
        } else {
            userService.addUser(user);
        }
        return Result.successMap("注册成功");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 用户登录 0: 学生  1：维修人员  2：管理员 3：系统管理员
     * @Date 16:09 2019/10/28
     * @Param [requestUser]
     **/
    @ApiOperation(value = "用户登录", notes = "用户登录 0: 学生  1：维修人员  2：管理员 3：系统管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "用户类型", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/user/login")
    public Map<String, Object> login(@RequestBody User user, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        String pwd = AESUtils.AESEncrypt(user.getPassword(), KEY);
        if (null == userService.findByUsername(user.getUsername())) {
            return Result.failMap("用户不存在,请先去注册");
        }
        if (!userService.findByUsername(user.getUsername()).getType().equals(user.getType())) {
            return Result.failMap("请选择正确的登录类型");
        }
        if (userService.findByUsername(user.getUsername()).getType().equals(user.getType()) && userService.findByUsername(user.getUsername()).getPassword().equals(pwd)) {
            session.setAttribute("user", user);
            request.getSession().setAttribute("user", user);
            return tokenService.getToken(user);
        } else {
            return Result.failMap("用户名或密码错误");
        }
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 修改密码
     * @Date 10:08 2019/10/29
     * @Param [user]
     **/
    @ApiOperation(value = "修改密码", notes = "用户登录之后才可以修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pwdNew", value = "新密码", required = true, dataType = "String")
    })
    @PutMapping(value = "/user/changePwd")
    public Map<String, Object> changePwd(@RequestBody User user) throws Exception {
        //对新密码进行加密传输
        String newPwd = AESUtils.AESEncrypt(user.getPwdNew(), KEY);
        //判断当前用户输入的旧密码是否与数据库中该用户的密码一致
        if (Objects.equals(AESUtils.AESEncrypt(user.getPassword(), KEY), userService.findByUsername(user.getUsername()).getPassword())) {
            user.setPwdNew(newPwd);
            userService.changePwd(user);
            return Result.successMap("密码修改成功");
        }
        return Result.failMap("密码修改失败");

    }


    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 个人信息
     * @Date 10:12 2019/10/31
     * @Param [userName]
     **/
    @ApiOperation(value = "显示个人信息", notes = "显示信息如下")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String"),
            @ApiImplicitParam(name = "depart", value = "系别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "special", value = "专业", required = true, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "学号/工号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "联系电话", required = true, dataType = "String"),
            @ApiImplicitParam(name = "register_time", value = "注册时间", required = true, dataType = "String")

    })
    @GetMapping(value = "/user/show")
    public Map<String, Object> showSelfInfo(@RequestBody String userName) {
        if (null != userService.showSelfInfo(userName)) {
            User user = userService.showSelfInfo(userName);
            return Result.successMap(user);
        }
        return Result.failMap("加载异常,请稍后");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 修改资料
     * @Date 16:49 2019/10/30
     * @Param [user]
     **/
    @ApiOperation(value = "用户修改资料", notes = "用户登录之后才可以修改资料")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别 0:女  1：男", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "联系电话 前端限制为 11位", required = true, dataType = "String"),

    })
    @PutMapping(value = "/user/alterUser")
    public Map<String, Object> alterUser(@RequestBody User user) {
        if (userService.showSelfInfo(user.getUsername()).getUsername().equals(user.getUsername())) {
            if (userService.findByUsername(user.getUsername()).getUsername().equals(user.getUsername())) {
                return Result.failMap("用户名已存在,请重新输入");
            }
            userService.alterUser(user);
            return Result.successMap(user);
        }
        return Result.failMap("修改失败");
    }





    @RequestMapping("/login_p")
    public Result login() {
        return Result.fail("尚未登录，请登录!");
    }
    @GetMapping("/employee/advanced/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/employee/basic/hello")
    public String basicHello() {
        return "basicHello";
    }
}
