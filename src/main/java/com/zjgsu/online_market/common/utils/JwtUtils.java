package com.zjgsu.online_market.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {
    private static String secret = "5s15d1512@S3SssdsD";
    private long expire;
    private String header;

    /**
     * 生成jwt token
     */
    public static String generateToken(Map<String, String> mp) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        mp.forEach((k, v) -> builder.withClaim(k, v));
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(secret));
        return token;
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }


}