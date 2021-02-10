package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.exception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        if(UserService.isAuthenticated()) return "redirect:/";

        log.info("New User Registration Page Requested.");

        model.addAttribute("user", new UserRegistrationCommand());

        return "registration";
    }

    @PreAuthorize("not isAuthenticated()")
    @PostMapping
    public String registerUserAccount(
            @Valid @ModelAttribute("user") UserRegistrationCommand user,
            BindingResult result, Model model) {

        log.info("New User Save Controller called.");

        if(result.hasErrors()){
            log.warn("New User page post Validation error.");

            return "registration";
        }

        try {
            User registered = userService.registerNewUserAccount(user);
            log.info("New User Saved.");
        } catch (UserAlreadyExistException uaeEx) {
            log.info(uaeEx.getMessage() + " " + uaeEx.getCause());
            model.addAttribute("message", uaeEx.getMessage());
            return "registration";
        }

        return "redirect:/";
    }
}
