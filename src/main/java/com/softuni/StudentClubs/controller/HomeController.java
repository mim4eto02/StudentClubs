package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    public HomeController() {

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

//    @GetMapping("/contact")
//    public ModelAndView contact() {
//
//        return new ModelAndView("contact");
//    }
}
