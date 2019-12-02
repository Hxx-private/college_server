package com.hxx.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hxx.demo.entity.Result;
import com.hxx.demo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Hxx
 */
@Service
@Transactional
public class TokenService {

    public Map<String,Object> getToken(User user) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUsername()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return Result.successMap(token);
    }
}
