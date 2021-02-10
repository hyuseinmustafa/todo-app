package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    /*
     * This method prevents accessing to the login page if it is already logged in
     */
    @GetMapping
    public String getUserLoginPage() {
        if(UserService.isAuthenticated()) return "redirect:/";
        return "login";
    }
}
