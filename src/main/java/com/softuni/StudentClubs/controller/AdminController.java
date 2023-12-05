package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.models.entities.UserEntity;
import com.softuni.StudentClubs.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AdminController {

   private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin-users";
    }

    @GetMapping("/admin/user/{userId}")
    public String getUserById(@PathVariable Long userId, Model model) {
        UserEntity user = adminService.getUserById(userId);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/admin/makeAdmin/{userId}")
    public String makeAdmin(@PathVariable Long userId) {
        adminService.makeAdmin(userId);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/removeAdmin/{userId}")
    public String removeAdmin(@PathVariable Long userId) {
        adminService.removeAdmin(userId);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/activateUser/{userId}")
    public String activateUser(@PathVariable Long userId) {
        adminService.activateUser(userId);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/deactivateUser/{userId}")
    public String deactivateUser(@PathVariable Long userId) {
        adminService.deactivateUser(userId);

        return "redirect:/admin/users";
    }
}