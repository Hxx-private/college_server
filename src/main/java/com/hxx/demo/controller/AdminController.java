package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.annotation.UserLoginToken;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.User;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.AESUtils;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Hxx
 */
@Api(value = "管理员事务控制层")
@RestController
@RequestMapping("/api")
public class AdminController {
    private static final String KEY = "zhonghuan13xxxxx";
    @Autowired
    private UserService userService;


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据学号查找用户信息
     * @Date 14:30 2019/10/30
     * @Param [number]
     **/
    @ApiOperation(value = "根据学号或工号查找用户信息")
    @ApiImplicitParam(name = "number", value = "用户学号/工号", required = true, dataType = "String")
    @GetMapping(value = "/findNumber")
    public Map<String, Object> finfByUserNumber(@RequestBody String number) {
        if (!userService.findByNumber(number).isEmpty()) {
            return Result.successMap(userService.findByNumber(number));
        }
        return Result.failMap("该用户不存在");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 根据真实姓名查找用户
     * @Date 16:06 2019/10/30
     * @Param [name]
     **/
    @ApiOperation(value = "根据真实姓名查找用户")
    @ApiImplicitParam(name = "name", value = "真实姓名", required = true, dataType = "String")
    @GetMapping(value = "/findByName")
    public Map<String, Object> findByName(@RequestBody String name) {
        if (null != userService.findByName(name)) {
            return Result.successMap(userService.findByName(name));
        }
        return Result.failMap("该用户不存在");
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询所有用户
     * @Date 11:46 2019/11/6
     * @Param [pageNum, pageSize]当前页，默认为1,size 当前每页显示行数，默认为15
     **/
    @ApiOperation(value = "查询所有用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1",  dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
    @GetMapping("user/findAllUser")
    public Map<String, Object> findAllUser( Integer pageNum, Integer pageSize) {
        if (userService.findAll().isEmpty()) {
            return Result.failMap("用户信息为空");
        }
        //pageNum：当前页数   pageSize：当前页需要显示的数量
        PageHelper.startPage(pageNum, pageSize);
        return Result.successMap(userService.findAll());
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 添加维修人员或管理员
     * @Date 16:55 2019/11/8
     * @Param [user]
     **/
    @ApiOperation(value = "添加管理员")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "user_name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "真实姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "电话", required = true, dataType = "String"),
    }
    )
    @PostMapping("/createUser")
    public Map<String, Object> createUser(@RequestBody User user) throws Exception {
        //设置工号
        user.setNumber(IdUtils.getNumber());
        //设置用户类型为管理员
        user.setType(2);
        //对密码进行加密传输
        user.setPassword(AESUtils.AESEncrypt(user.getPassword(), KEY));
        //设置添加时间
        user.setRegisterTime(DateUtils.getSysTime());
        if (null != userService.findByUsername(user.getUserName())) {
            return Result.failMap("用户名已经存在,请重新输入");
        }
        userService.createUser(user);
        return Result.successMap(user);
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据用户名删除用户
     * @Date 15:29 2019/11/7
     * @Param [userName]
     **/
    @ApiOperation(value = "根据用户名删除用户")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    @DeleteMapping("/delByUserName{userName}")
    public Map<String, Object> delByUserName(@PathVariable("userName") String userName) {
        userService.delByUserName(userName);
        if (userService.findByUsername(userName) == null) {
            return Result.successMap("删除成功");
        }

        return Result.failMap("删除失败");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据工号或者学号删除用户
     * @Date 15:30 2019/11/7
     * @Param [number]
     **/

    @ApiOperation(value = "根据工号或者学号删除用户")
    @ApiImplicitParam(name = "number", value = "学号/工号", required = true, dataType = "String")
    @DeleteMapping("/delByNumber/{number}")
    public Map<String, Object> delByNumber(@PathVariable("number") String number) {
        userService.delByNumber(number);
        if (userService.findByNumber(number).isEmpty()) {
            return Result.successMap("删除成功");
        }
        return Result.failMap("删除失败");
    }
}
