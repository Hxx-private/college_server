package com.hxx.demo.controller;

import com.hxx.demo.entity.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2017/12/29.
 */
@RestController
public class RegLoginController {
    @RequestMapping("/login_p")
    public RespBean login() {

        return RespBean.error("尚未登录，请登录!");

    }
}
