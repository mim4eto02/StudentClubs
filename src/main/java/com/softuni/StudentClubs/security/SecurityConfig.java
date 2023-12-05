package com.softuni.StudentClubs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
                .antMatchers("/register", "/login", "/clubs", "/events", "/css/**", "/js?**", "/img/**", "/fonts/**", "/favicon.ico", "/contact", "/about", "/privacy", "/terms")
                .permitAll()
//                .antMatchers("/admin/**")
//                .hasAuthority("ADMIN")
                .antMatchers("/clubs/new", "/clubs/edit/**", "/clubs/delete/**", "/clubs/join/**", "/clubs/leave/**")
                .hasAnyAuthority("USER")
                .antMatchers("/events/create", "/events/edit/**", "/events/delete/**", "/events/join/**", "/events/leave/**")
                .hasAnyAuthority("USER")
                .antMatchers("/users/profile/**", "/users/edit-profile/**", "/users/change-password/**")
                .hasAuthority("USER")
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/clubs")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(logout-> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );


        return httpSecurity.build();
    }


    public void  configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
