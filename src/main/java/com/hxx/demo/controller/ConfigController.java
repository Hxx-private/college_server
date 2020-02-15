package com.hxx.demo.controller;

import com.hxx.demo.entity.*;
import com.hxx.demo.service.*;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是一个只要登录就能访问的Controller
 * 主要用来获取一些配置信息
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private DepartService departService;
    @Autowired
    private SpecialService specialService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private  UserService userService;
    @GetMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByUserId();

    }

    @GetMapping("/user")
    public User currentUser() {
        return UserUtils.getCurrentUser();
    }


    /**
     * @return com.hxx.demo.entity.RespBean
     * @Author Hxx
     * @Description //TODO 下拉菜单
     * @Date 14:06 2019/12/23
     * @Param []
     **/
    @GetMapping("/depart")
    public RespBean getDepart() {
        List<Depart> list = departService.find();
        return RespBean.ok("", list);
    }

    @GetMapping("/special/{departId}")
    public RespBean getSpecial(@PathVariable Integer departId) {
        List<Special> list = specialService.find(departId);
        return RespBean.ok("", list);
    }

    @GetMapping("/room")
    public RespBean getRoom() {
        List<Room> list = roomService.getRoom();
        return RespBean.ok("", list);
    }

    @GetMapping("/build")
    public RespBean getBuild() {
        List<Build> list = roomService.getBuild();
        return RespBean.ok("", list);
    }


}
