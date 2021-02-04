package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.customexception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.service.UserDetailsServiceImpl;
import com.hyusein.mustafa.todoapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        log.info("New User Registration Page Requested.");

        model.addAttribute("user", new UserRegistrationCommand());

        return "registration";
    }

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
