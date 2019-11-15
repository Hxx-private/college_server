package com.hxx.demo.controller;

import com.hxx.demo.entity.Menu;
import com.hxx.demo.entity.User;
import com.hxx.demo.service.MenuService;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByUserId();
    }

    @GetMapping("/user")
    public User currentUser() {
        return UserUtils.getCurrentUser();
    }
}
