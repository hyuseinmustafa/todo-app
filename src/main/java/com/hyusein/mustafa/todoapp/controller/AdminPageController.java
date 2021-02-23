package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.PrivilegeRepository;
import com.hyusein.mustafa.todoapp.repository.RoleRepository;
import com.hyusein.mustafa.todoapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AdminPageController(PrivilegeRepository privilegeRepository, RoleRepository roleRepository, UserService userService) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping({"","user","role/{id}"})
    String getPrivileges(@PathVariable(value = "id",required = false) Long id,
                         @ModelAttribute(value = "username", binding = false) String username, Model model){

        model.addAttribute("allPrivileges", privilegeRepository.findAll());
        model.addAttribute("allRoles", roleRepository.findAll());
        if(id != null)
            model.addAttribute("role", roleRepository.findById(id).orElse(null));
        if(username != null)
            model.addAttribute("user", userService.findByUsername(username));

        return "adminpanel/view";
    }

    @PostMapping("/save/{id}")
    String savePrivileges(@Valid @ModelAttribute("role") Role role,
                          @PathVariable("id") Long roleId, Model model, BindingResult result) {
        roleRepository.findById(role.getId()).ifPresent(val -> {
            val.setPrivileges(role.getPrivileges());
            roleRepository.save(val);
        });
        return "redirect:/admin";
    }

    @PostMapping("/user/save")
    String saveUserRole(@ModelAttribute("user") User user,
                          Model model, BindingResult result) {
        userService.saveUserRole(user.getUsername(), user.getRoles());
        return "redirect:/admin";
    }
}
