package com.zjgsu.online_market.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjgsu.online_market.common.annotations.RoleRequired;
import com.zjgsu.online_market.entity.Role;
import com.zjgsu.online_market.entity.Users;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RoleInterceptor implements HandlerInterceptor {
    private final static Integer ADMIN_ROLE = 0;
    @Autowired
    private HttpSession httpSession;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RoleRequired annotation = method.getAnnotation(RoleRequired.class);
        if (annotation == null)
        {
            return true;
        }

        Map<String,Object> map = new HashMap<>();
        Role role = (Role) httpSession.getAttribute("role");
        if (role == null) {
            log.error("权限认证过期");
            map.put("state",403);
            map.put("msg","权限认证过期"+"\n请重新登陆");
            String s = new ObjectMapper().writeValueAsString(map);
            response.getWriter().println(s);
            return false;
        }
        if (!role.getRole().equals(ADMIN_ROLE))
        {
            log.error("权限不足");
            map.put("msg","权限不足"+"\n无法访问");
            map.put("state",403);
            String s = new ObjectMapper().writeValueAsString(map);
            response.getWriter().println(s);
            return false;
        }
        return true;
    }
}
