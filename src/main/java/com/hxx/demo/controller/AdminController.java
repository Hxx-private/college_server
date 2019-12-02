package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.RespBean;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.User;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Api(value = "管理员事务控制层")
@RestController
@RequestMapping("/user")
public class AdminController {
    Map<String, Object> map = new HashMap<>();
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
    public Map<String, Object> findByUserNumber(@RequestBody String number) {
        if (!userService.findByNumber(number).isEmpty()) {
            return Result.successMap(userService.findByNumber(number));
        }
        return Result.failMap("该用户不存在");
    }
    @GetMapping("/findByKeyWords")
    public RespBean findByKeyWords(Integer pageNum,Integer pageSize,String keywords){
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.getUsersByKeywords(keywords);
        int total = userService.total();
        map.put("data", list);
        map.put("total", total);

        return RespBean.ok("",map);
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
    public RespBean findByName(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.findByName(name);
        int total = userService.total();
        map.put("data", list);
        map.put("total", total);
        return RespBean.ok("", map);

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
            @ApiImplicitParam(name = "pageNum", value = "当前页,默认为1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "当前每页显示行数", dataType = "Integer")
    }
    )
    @GetMapping("/list")
    public RespBean findAllUser(Integer pageNum, Integer pageSize) {
        //pageNum：当前页数   pageSize：当前页需要显示的数量
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.findAll();
        int total = userService.total();
        map.put("data", userList);
        map.put("total", total);
        return RespBean.ok("", map);
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
    public RespBean createUser(@RequestBody User user) throws Exception {
        //设置工号
        user.setNumber(IdUtils.getNumber());
        //设置密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(user.getPassword());
        user.setPassword(encode);
        //设置添加时间
        user.setRegisterTime(DateUtils.getSysTime());
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            return RespBean.error("用户名已经存在,请重新输入");
        }
        userService.createUser(user);
        return RespBean.ok("添加成功");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据userName删除用户
     * @Date 15:29 2019/11/7
     * @Param [userName]
     **/
    @ApiOperation(value = "根据userName删除用户")
    @ApiImplicitParam(name = "userName", value = "userName", required = true, dataType = "String")
    @GetMapping("/delByUserName")
    public RespBean delByuserName(String userName) {
        userService.delByUserName(userName);
        return RespBean.ok("删除成功");
    }
    @GetMapping("/getUsersByKeywords")
    public RespBean getUsersByKeywords(String keywords) {
        return RespBean.ok("", userService.getUsersByKeywords(keywords));
    }

}
