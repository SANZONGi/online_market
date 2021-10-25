package com.zjgsu.online_market.config;

import com.zjgsu.online_market.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@PropertySource("classpath:application.yml")
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${xjj.imgfilepath}")
    private String LocalImgPath;

    @Autowired
    private JWTInterceptor jwtInterceptor;

//    jwt拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("users/checkuser");
    }


    //资源访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String res = "file:/home/ubuntu/imgs/";
        if (File.separator.equals("/")) {
//            System.out.println("linux");
        } else {
            res = "file:///"+LocalImgPath;
//            System.out.println("other");
        }
        registry.addResourceHandler("/static/**").addResourceLocations(res);
//        if (!registry.hasMappingForPattern("/**")) {
//            registry.addResourceHandler("/**").addResourceLocations(
//                    CLASSPATH_RESOURCE_LOCATIONS);
//        }

    }
}
