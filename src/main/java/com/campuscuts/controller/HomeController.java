package com.campuscuts.controller;

import com.campuscuts.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SchoolService schoolService;

    public HomeController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        return "home";
    }
}
