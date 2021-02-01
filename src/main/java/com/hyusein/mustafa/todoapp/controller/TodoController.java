package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.converter.TodoToTodoCommandConverter;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository todoRepository;
    private final ProjectRepository projectRepository;

    public TodoController(TodoRepository todoRepository, ProjectRepository projectRepository) {
        this.todoRepository = todoRepository;
        this.projectRepository = projectRepository;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        //dataBinder.setDisallowedFields("id");
    }

    @GetMapping({"/new"})
    public String getNewToDoPage(Model model){
        log.debug("Add new ToDo Page Requested.");

        model.addAttribute("neworedit_todo", new TodoCommand());
        List<Project> project_list = new ArrayList<>();
        projectRepository.findAll().forEach(project_list::add);
        model.addAttribute("project_list", project_list);

        return "todo/new";
    }

    @GetMapping("/{id}/edit")
    public String getEditToDoPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Todo.");

        model.addAttribute("neworedit_todo", new TodoToTodoCommandConverter().convert(todoRepository.findById(id).orElse(null)));
        List<Project> project_list = new ArrayList<>();
        projectRepository.findAll().forEach(project_list::add);
        model.addAttribute("project_list", project_list);

        return "todo/new";
    }

    @GetMapping("/{id}/done")
    public String getDoneToDoButton(@PathVariable("id") Long id, Model model){
        log.debug("Done Todo. ID: " + id);

        Optional<Todo> todoOptional = todoRepository.findById(id);
        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            todo.setStatus(ToDoStatus.FINISHED);
            todoRepository.save(todo);
        }

        return "redirect:/";
    }

    @PostMapping({"/save"})
    public String saveNewToDoPage(@Valid @ModelAttribute("neworedit_todo") TodoCommand todo, BindingResult result, Model model){
        if(result.hasErrors()){
            log.debug("New ToDo page post Validation error.");
            List<Project> project_list = new ArrayList<>();
            projectRepository.findAll().forEach(project_list::add);
            model.addAttribute("project_list", project_list);

            return "todo/new";
        }
        log.debug("ToDo saved id: " + todo.getId());

        todoRepository.save(new TodoCommandToTodoConverter().convert(todo));

        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteToDoPage(@PathVariable("id") Long id){
        log.debug("ToDo delete. id: " + id);

        todoRepository.deleteById(id);

        return "redirect:/";
    }
}
