package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index() {

        return new ModelAndView("index");
    }

    @GetMapping("/privacy")
    public ModelAndView privacy() {

        return new ModelAndView("privacy");
    }

    @GetMapping("/terms")
    public ModelAndView terms() {

        return new ModelAndView("terms");
    }
}
