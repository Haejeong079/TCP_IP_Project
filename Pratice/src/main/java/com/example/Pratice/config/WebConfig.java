package com.example.Pratice.config;

import com.example.Pratice.session.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public SessionInterceptor sessionInterceptor(){
//        return new SessionInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(sessionInterceptor())
//                .addPathPatterns("/dashboard"); //대시보드 페이지에만 적용하도록 설정함
//    }
}
