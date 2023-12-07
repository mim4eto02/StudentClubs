package com.softuni.StudentClubs.config;

import com.softuni.StudentClubs.interceptors.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/admin/**")  // Apply the interceptor to URLs starting with "/admin"
                .addPathPatterns("/users/**")  // Apply the interceptor to URLs starting with "/user"
                .excludePathPatterns("/login");  // Exclude the login page from the interceptor
    }
}
