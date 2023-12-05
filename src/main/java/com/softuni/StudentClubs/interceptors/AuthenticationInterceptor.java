package com.softuni.StudentClubs.interceptors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
            return true;
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("USER"))) {
            response.sendRedirect(request.getContextPath().concat("/"));
        } else {
            response.sendRedirect(request.getContextPath().concat("/login"));
            return false;
        }
return false;
    }


}
