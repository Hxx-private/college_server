package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
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
@RestController
@RequestMapping("/user")
public class AdminController  {
    Map<String, Object> map = new HashMap<>();

    /**
     * @Author Hxx
     * @Description //TODO 根据指定字段查询用户
     * @Date 11:53 2019/12/2
     * @Param
     * @return
     **/
    @Autowired
    private UserService userService;
    @PostMapping(value = "findByKeyWords",consumes="application/json;charset=UTF-8")
    public HttpEntity findByKeyWords( @RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        List<User> list = this.userService.getGrid(gridJson);
        int total = list.size();
        grid.setData(list);
        grid.setPageIndex(gridJson.getPageIndex());
        grid.setTotalCount(total);
        grid.setPageItemCount(grid.getPageItemCount());
        httpEntity.setData(grid);
        httpEntity.setStatus(200);
        return httpEntity;
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
        return RespBean.ok("添加成功", user);
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
}
