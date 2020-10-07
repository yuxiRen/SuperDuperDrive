package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUpView() {
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("user") User user, Model model) {
        String error = null;
        //check whether username already exist
        if (!userService.isAvailable(user.getUsername())) {
            error = "The username is already exist!";
        }
        //check whether signup successfully
        if (error == null) {
            int row = userService.createUser(user);
            if (row < 0) {
                error = "Signup failed!";
            }
        }
        if (error == null) {
            model.addAttribute("signUpSuccess", true);
        } else {
            model.addAttribute("signUpError", error);
            return "redirect:signup?error";
        }

        return "redirect:login?success";
    }
}
