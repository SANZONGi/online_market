package com.zjgsu.online_market.config;
import com.zjgsu.online_market.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //jwt拦截
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/good/frozen/**")
//                .addPathPatterns("/users/changepassword")
//                .addPathPatterns("/orders/**");
//    }


    //资源访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String res = "file:/home/ubuntu/imgs/";
        if (File.separator.equals("/")) {
//            System.out.println("linux");
        } else {
            res = "file:///D:/图片/";
//            System.out.println("other");
        }
        registry.addResourceHandler("/static/**").addResourceLocations(res);
//        if (!registry.hasMappingForPattern("/**")) {
//            registry.addResourceHandler("/**").addResourceLocations(
//                    CLASSPATH_RESOURCE_LOCATIONS);
//        }

    }
}
