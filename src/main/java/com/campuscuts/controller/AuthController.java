package com.campuscuts.controller;

import com.campuscuts.dto.SignupForm;
import com.campuscuts.service.SchoolService;
import com.campuscuts.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserRegistrationService userRegistrationService;
    private final SchoolService schoolService;

    public AuthController(UserRegistrationService userRegistrationService, SchoolService schoolService) {
        this.userRegistrationService = userRegistrationService;
        this.schoolService = schoolService;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        model.addAttribute("schools", schoolService.findAll());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm") SignupForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("schools", schoolService.findAll());
            return "auth/signup";
        }
        try {
            userRegistrationService.register(form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("schools", schoolService.findAll());
            return "auth/signup";
        }
        return "redirect:/login?signedUp";
    }
}
