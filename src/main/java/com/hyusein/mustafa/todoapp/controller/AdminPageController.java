package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.repository.PrivilegeRepository;
import com.hyusein.mustafa.todoapp.repository.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    public AdminPageController(PrivilegeRepository privilegeRepository, RoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping({"","/{id}"})
    String getPrivileges(@PathVariable(value = "id",required = false) Long id, Model model){

        model.addAttribute("allPrivileges", privilegeRepository.findAll());
        model.addAttribute("allRoles", roleRepository.findAll());
        if(id != null)
            model.addAttribute("role", roleRepository.findById(id).orElse(null));

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
}
