//package com.zjgsu.online_market.config;
//
//import com.zjgsu.online_market.entity.MyParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import java.io.File;
//
//
//@Configuration
//public class MyWebConfig extends WebMvcConfigurationSupport {
//    //    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
////            "classpath:/META-INF/resources/", "classpath:/resources/",
////            "classpath:/static/", "classpath:/public/" };
//    @Autowired
//    private MyParam xjj;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String res = "file:/home/ubuntu/imgs/";
//        if (File.separator.equals("/")) {
////            System.out.println("linux");
//        } else {
//            res = "file:///D:/图片/";
////            System.out.println("other");
//        }
//        registry.addResourceHandler("/static/**").addResourceLocations(res);
////        if (!registry.hasMappingForPattern("/**")) {
////            registry.addResourceHandler("/**").addResourceLocations(
////                    CLASSPATH_RESOURCE_LOCATIONS);
////        }
//
//    }
//
//}
