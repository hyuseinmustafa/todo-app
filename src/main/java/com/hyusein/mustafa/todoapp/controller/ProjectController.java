package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.converter.ProjectCommandToProject;
import com.hyusein.mustafa.todoapp.converter.ProjectToProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping({"","/"})
    public String getProjectsPage(Model model) {
        log.debug("Projects Page Requested.");

        List<Project> project_list = new ArrayList<>();
        projectRepository.findAll().forEach(project_list::add);
        model.addAttribute("project_list", project_list);

        return "project/list";
    }

    @GetMapping("/new")
    public String getNewProjectPage(Model model) {
        model.addAttribute("neworedit_project", new ProjectCommand());

        return "project/new";
    }

    @GetMapping("/{id}/edit")
    public String getEditToDoPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Project.");

        model.addAttribute("neworedit_project", new ProjectToProjectCommand().convert(projectRepository.findById(id).orElse(null)));

        return "project/new";
    }
    @PostMapping({"/save"})
    public String saveNewToDoPage(@Valid @ModelAttribute("neworedit_project") ProjectCommand project, BindingResult result) {
        if (result.hasErrors()) {
            log.debug("New Project page post Validation error.");

            return "project/new";
        }
        log.debug("ToDo saved id: " + project.getId());

        projectRepository.save(new ProjectCommandToProject().convert(project));

        return "redirect:/project";
    }

    @GetMapping("/{id}/delete")
    public String deleteToDoPage(@PathVariable("id") Long id){
        log.debug("Project delete. id: " + id);

        projectRepository.deleteById(id);

        return "redirect:/project";
    }
}
