package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.dto.UserEditDto;
import com.softuni.StudentClubs.models.UserEntity;
import com.softuni.StudentClubs.security.SecurityUtil;
import com.softuni.StudentClubs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/details")
    public String getUserDetails(Model model) {
        UserEntity userEntity = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            userEntity = userService.findByUsername(username);
            model.addAttribute("user", userEntity);
        }
        model.addAttribute("user", userEntity);
        return "user-details";
    }

    @GetMapping("/user/edit")
    public String getUserEdit(Model model) {
        UserEntity userEntity = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            userEntity = userService.findByUsername(username);
            model.addAttribute("user", userEntity);
        }
        model.addAttribute("user", userEntity);
        return "user-edit";
    }

    @PostMapping("/user/edit")
    public String postUserEdit(UserEditDto userEditDto) {
        String username = SecurityUtil.getSessionUser();
        if (username != null) {
            userService.editUser(userEditDto, username);
        }
        return "redirect:/logout";
    }

}
