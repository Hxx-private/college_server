package com.hxx.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxx.demo.entity.RespBean;
import com.hxx.demo.service.UserService;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;

import java.io.PrintWriter;

/**
 * @Author Hxx
 * @Description //TODO
 * @Date 15:27 2019/11/20
 * @Param
 * @return
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/system/user/reg", "/user/createUser", "/login_p","/pic/*.png","/pic/*.jpeg","/pic/*.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(metadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/login")
                .usernameParameter("uName").passwordParameter("password")
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    RespBean respBean;
                    if (e instanceof BadCredentialsException ||
                            e instanceof UsernameNotFoundException) {
                        respBean = RespBean.error("用户名或者密码输入错误!");
                    } else if (e instanceof LockedException) {
                        respBean = RespBean.error("账号被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        respBean = RespBean.error("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        respBean = RespBean.error("账号过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        respBean = RespBean.error("账号被禁用，请联系管理员!");
                    } else {
                        respBean = RespBean.error("登录失败!");
                    }
                    resp.setStatus(401);
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = resp.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .successHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    RespBean respBean = RespBean.ok("登录成功!", UserUtils.getCurrentUser());
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = resp.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    RespBean respBean = RespBean.ok("注销成功!");
                    ObjectMapper om = new ObjectMapper();
                    PrintWriter out = resp.getWriter();
                    out.write(om.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(deniedHandler);

    }
}
