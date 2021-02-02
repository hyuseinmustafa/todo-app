package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping({"","/"})
    public String getProjectsPage(Model model) {
        log.debug("Projects Page Requested.");

        model.addAttribute("project_list", projectService.findAll());

        return "project/list";
    }

    @GetMapping("/new")
    public String getNewProjectPage(Model model) {
        log.debug("New Projects Page Requested.");

        model.addAttribute("neworedit_project", new ProjectCommand());

        return "project/new";
    }

    @GetMapping("/{id}/edit")
    public String getEditProjectPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Project Page Requested.");

        model.addAttribute("neworedit_project", projectService.findById(id));

        return "project/new";
    }

    @PostMapping({"/save"})
    public String saveNewProjectPage(@Valid @ModelAttribute("neworedit_project") ProjectCommand project, BindingResult result) {
        if (result.hasErrors()) {
            log.debug("New Project page post Validation error.");

            return "project/new";
        }

        ProjectCommand savedProject = projectService.save(project);

        log.debug("Project saved id: " + savedProject.getId());

        return "redirect:/project";
    }

    @GetMapping("/{id}/delete")
    public String deleteProjectPage(@PathVariable("id") Long id){
        projectService.deleteById(id);

        log.debug("Project delete. id: " + id);

        return "redirect:/project";
    }
}
