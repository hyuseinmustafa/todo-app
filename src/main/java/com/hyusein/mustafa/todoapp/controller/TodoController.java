package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import com.hyusein.mustafa.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final ProjectService projectService;

    public TodoController(TodoService todoService, ProjectService projectService) {
        this.todoService = todoService;
        this.projectService = projectService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        //dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"/new"})
    public String getNewToDoPage(Model model){
        log.debug("Add new ToDo Page Requested.");

        model.addAttribute("neworedit_todo", new TodoCommand());
        model.addAttribute("project_list", projectService.findAll());

        return "todo/new";
    }

    @GetMapping("/{id}/edit")
    public String getEditToDoPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Todo.");

        model.addAttribute("neworedit_todo", todoService.findById(id));
        model.addAttribute("project_list", projectService.findAll());

        return "todo/new";
    }

    @GetMapping("/{id}/done")
    public String getDoneToDoDoneButton(@PathVariable("id") Long id, Model model){
        log.debug("Done Todo. ID: " + id);

        todoService.done(id);

        return "redirect:/";
    }

    @PostMapping({"/save"})
    @Transactional
    public String saveNewToDoPage(@Valid @ModelAttribute("neworedit_todo") TodoCommand todo, BindingResult result, Model model){
        if(result.hasErrors()){
            log.debug("New ToDo page post Validation error.");

            model.addAttribute("project_list", projectService.findAll());

            return "todo/new";
        }

        TodoCommand savedTodo = todoService.save(todo);

        log.debug("ToDo saved id: " + savedTodo.getId());

        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteToDoPage(@PathVariable("id") Long id){
        log.debug("ToDo delete. id: " + id);

        todoService.deleteById(id);

        return "redirect:/";
    }
}
