package com.hxx.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxx.demo.entity.*;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.DateUtils;
import com.hxx.demo.utils.IdUtils;
import com.hxx.demo.utils.PoiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class AdminController {
    Map<String, Object> map = new HashMap<>();
    @Autowired
    private UserService userService;

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据指定字段查询用户
     * @Date 11:53 2019/12/2
     * @Param
     **/
    @PostMapping(value = "findByKeyWords", consumes = "application/json;charset=UTF-8")
    public HttpEntity findByKeyWords(@RequestBody GridRequest gridJson) {
        HttpEntity httpEntity = new HttpEntity();
        Grid grid = new Grid();
        PageHelper.startPage(gridJson.getPageNum(), gridJson.getPageSize());
        List<User> list = this.userService.getGrid(gridJson);
        int total = list.size();
        grid.setData(list);
        grid.setPageNum(gridJson.getPageNum());
        grid.setTotal(total);
        grid.setPageSize(gridJson.getPageSize());
        httpEntity.setData(grid);
        httpEntity.setStatus(200);
        return httpEntity;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Hxx
     * @Description //TODO 查询所有用户
     * @Date 11:46 2019/11/6
     * @Param [pageNum, pageSize]当前页，默认为1,size 当前每页显示行数，默认为15
     **/
    @ApiOperation(value = "用户列表")
    @GetMapping("/list")
    public RespBean findAllUser(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.findAll();
        int total = userService.total();
        map.put("data", list);
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
    @ApiOperation(value = "添加其他用户类型")
    @PostMapping("/createUser")
    public RespBean createUser(@RequestBody User user) throws Exception {
        user.setNumber(IdUtils.getNumber());
        user.setPassword(new BCryptPasswordEncoder().encode("1234").trim());
        user.setRegisterTime(DateUtils.getSysTime());
        user.setEnabled(true);
        if (null != userService.findByUName(user.getUsername())) {
            return RespBean.error("用户名已经存在,请重新输入");
        }
        int i = userService.createUser(user);
        if (i > 0) {
            return RespBean.ok("添加成功", user);
        }
        return RespBean.error("添加失败");
    }

    /**
     * @return com.hxx.demo.entity.Result
     * @Author Hxx
     * @Description //TODO 根据uName删除用户
     * @Date 15:29 2019/11/7
     * @Param [uName]
     **/
    @DeleteMapping("/delByUserName/{uname}")
    public RespBean delByUserName(@PathVariable String uname) {
        int i = userService.delByUserName(uname);
        if (i > 0) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @PostMapping("/updateUser")
    public RespBean updateUser(@RequestBody User user) {
        if (null != userService.findindByUserNameNotSelf(user.getUsername(), user.getId())) {
            return RespBean.error("用户名已存在");
        }
        int i = userService.updateSelf(user);
        if (i > 0) {
            return RespBean.ok("保存成功!");
        }
        return RespBean.error("保存失败!");
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 详情
     * @Date 20:27 2019/12/21
     * @Param [id]
     **/
    @GetMapping("/findById/{id}")
    public RespBean findById(@PathVariable Integer id) {
        User user = userService.findById(id);
        return RespBean.ok("", user);
    }


    /**
     * @return org.springframework.http.ResponseEntity<byte [ ]>
     * @Author Hxx
     * @Description //TODO 导出用户信息
     * @Date 10:46 2019/12/12
     * @Param []
     **/
    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> exportUser() {
        return PoiUtils.exportUserExcel(userService.findAll());
    }

    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 15:58 2019/12/27
     * @Param [ids]
     **/
    @DeleteMapping("/deleteBatchd/{ids}")
    public RespBean deleteBatch(@PathVariable String ids) {
        if (userService.deleteBatch(ids)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
