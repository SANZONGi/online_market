package com.zjgsu.online_market.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.utils.JwtUtils;
import com.zjgsu.online_market.entity.Users;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * @author SANZONG
 * **/

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;

    public JwtInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否映射到方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
        //无此注解放行
        if (annotation == null)
        {
            return true;
        }
        Map<String, Object> map = new HashMap<>(10);

        //session检查
        Users users = (Users) request.getSession().getAttribute("user");
        System.out.println(users);
        if (users == null) {
            log.error("session过期");
            map.put("msg", "session过期" + "\n请重新登录");
            map.put("state", 405);
            String s = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(s);
            return false;
        }

        String token = request.getHeader("token");
        try {
            DecodedJWT decodedJwt = jwtUtils.verify(token);
            if (!decodedJwt.getClaim("uid").asString().equals(users.getUid().toString())) {
                log.error("用户不一致");
                map.put("msg", "用户不一致" + "\n无法访问");
                map.put("state", 405);
                String s = new ObjectMapper().writeValueAsString(map);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(s);
                return false;
            }
            return true;
        } catch (SignatureException e) {
            log.error(e.getMessage());
            map.put("msg", "无效签名" + "\n请重新登录");
        } catch (AlgorithmMismatchException e) {
            log.error(e.getMessage());
            map.put("msg", "token算法不一致" + "\n请重新登录");
        } catch (Exception e) {
            log.error(e.getMessage());
            if (token == null) {
                map.put("msg", "token为空" + "\n请重新登录");
            } else {
                map.put("msg", "token无效" + "\n请重新登录");
            }
        }
        //Method Not Allowed
        map.put("state", 405);
        String s = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(s);
        return false;
    }
}
