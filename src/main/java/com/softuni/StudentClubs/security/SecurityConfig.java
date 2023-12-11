package com.softuni.StudentClubs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.boot.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/clubs",  "/css/**", "/js?**", "/img/**", "/fonts/**", "/favicon.ico", "/contact", "/about", "/privacy", "/terms", "/", "articles")
                .permitAll()
//                .antMatchers("/admin/**")
//                .hasAuthority("ADMIN")

                .antMatchers("/clubs/new", "/clubs/edit/**", "/clubs/delete/**", "/clubs/join/**", "/clubs/leave/**")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/articles/new", "/articles/edit/**", "/articles/delete/**")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/events/create", "/events/edit/**", "/events/delete/**", "/events/join/**", "/events/leave/**")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/users/profile/**", "/users/edit-profile/**", "/users/change-password/**")
                .hasAnyAuthority("USER", "ADMIN")

                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(logout-> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );


        return httpSecurity.build();
    }


    @Bean
    public SessionRegistry sessionRegistry() {
        return new org.springframework.security.core.session.SessionRegistryImpl();
    }

    public void  configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}