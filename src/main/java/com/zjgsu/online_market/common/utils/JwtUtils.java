package com.zjgsu.online_market.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {
    @Value("${xjj.secret}")
    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(Map<String, String> mp) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 3);
        JWTCreator.Builder builder = JWT.create();
        mp.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }


}