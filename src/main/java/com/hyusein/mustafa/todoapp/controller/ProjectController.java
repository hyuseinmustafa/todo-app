package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.converter.ProjectToProjectCommandConverter;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('CREATE_PROJECT')")
    @GetMapping("/new")
    public String getNewProjectPage(Model model) {
        log.debug("New Projects Page Requested.");

        model.addAttribute("neworedit_project", new ProjectCommand());

        return "project/new";
    }

    @PreAuthorize("hasAuthority('EDIT_PROJECT')")
    @GetMapping("/{id}/edit")
    public String getEditProjectPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Project Page Requested.");

        model.addAttribute("neworedit_project",
                new ProjectToProjectCommandConverter().convert(projectService.findById(id)));

        return "project/new";
    }

    @PreAuthorize("(hasAuthority('EDIT_PROJECT') and #project.id != null) or" +
            " (hasAuthority('CREATE_PROJECT') and #project.id == null)")
    @PostMapping({"/save"})
    public String saveNewProjectPage(@Valid @ModelAttribute("neworedit_project") ProjectCommand project,
                                     BindingResult result) {
        if (result.hasErrors()) {
            log.debug("New Project page post Validation error.");

            return "project/new";
        }

        Project savedProject = projectService.save(project);

        log.debug("Project saved id: " + savedProject.getId());

        return "redirect:/project";
    }

    @PreAuthorize("hasAuthority('DELETE_PROJECT')")
    @GetMapping("/{id}/delete")
    public String deleteProjectPage(@PathVariable("id") Long id){
        projectService.deleteById(id);

        log.debug("Project delete. id: " + id);

        return "redirect:/project";
    }
}
