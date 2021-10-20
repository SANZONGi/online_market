package com.zjgsu.online_market.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.utils.JwtUtils;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否映射到方法
        if (!(handler instanceof HandlerMethod))
            return true;
        System.out.println("映射到方法");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
        //无此注解放行
        if (annotation == null)
            return true;
        System.out.println("进入");
        Map<String,Object> map = new HashMap<>();
        String token = request.getHeader("token");
        try {
            JwtUtils.verify(token);
            return true;
        } catch (SignatureException e){
            e.printStackTrace();
            map.put("msg","无效签名"+"\n请重新登录");
        }catch (AlgorithmMismatchException e)
        {
            e.printStackTrace();
            map.put("msg","token算法不一致"+"\n请重新登录");
        } catch (Exception e)
        {
            e.printStackTrace();
            map.put("msg","token无效"+"\n请重新登录");
        }
        map.put("state",false);
        String s = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(s);
        return false;
    }
}
