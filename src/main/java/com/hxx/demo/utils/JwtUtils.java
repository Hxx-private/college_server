package com.hxx.demo.utils;/**
 * ClassName: JwtUtil <br/>
 * Description: <br/>
 * date: 2019/10/10 上午9:48<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: jwtdemo
 *
 * @description: JwtUtil  Json Web Token Util
 *
 * @author: zxb
 *
 * @create: 2019-10-10 09:48
 **/
public class JwtUtils {
    static final String SECRET = "ThisisASecret";
    public static String generateToken(String userName){
        HashMap<String,Object> map = new HashMap<>();
        map.put("userName",userName);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
        return "Bearer "+jwt;
    }

    public static Boolean validateToken(String token) {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
