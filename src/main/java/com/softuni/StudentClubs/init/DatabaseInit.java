package com.softuni.StudentClubs.init;

import com.softuni.StudentClubs.service.RoleService;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInit {

    private final RoleService roleService;

    private final UserService userService;

    public DatabaseInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            roleService.initRoles();
            userService.initAdmin();

        };
    }
}
