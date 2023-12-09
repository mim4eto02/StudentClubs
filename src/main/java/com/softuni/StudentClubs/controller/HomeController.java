package com.softuni.StudentClubs.controller;

import com.softuni.StudentClubs.service.ClubService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final ClubService clubService;

    public HomeController(ClubService clubService) {

        this.clubService = clubService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clubs", clubService.findAllClubs());
        return "index";


    }

    @GetMapping("/privacy")
    public ModelAndView privacy() {

        return new ModelAndView("privacy");
    }

    @GetMapping("/terms")
    public ModelAndView terms() {

        return new ModelAndView("terms");
    }

    @GetMapping("/about")
    public ModelAndView about() {

        return new ModelAndView("about");
    }

}
