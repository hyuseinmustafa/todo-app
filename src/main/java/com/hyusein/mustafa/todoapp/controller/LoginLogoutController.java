package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginLogoutController {

    /*
     * This method prevents accessing to the login page if it is already logged in
     */
    @GetMapping("/login")
    public String loginPage() {
        if(UserService.isAuthenticated()) return "redirect:/";
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/login?logout";
    }
}
