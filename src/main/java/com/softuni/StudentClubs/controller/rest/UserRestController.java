package com.softuni.StudentClubs.controller.rest;

import com.softuni.StudentClubs.dto.UserViewDto;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserViewDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
