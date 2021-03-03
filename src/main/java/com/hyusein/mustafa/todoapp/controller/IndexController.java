package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.enums.Priority;
import com.hyusein.mustafa.todoapp.enums.ToDoStatus;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Controller
public class IndexController {

    private final TodoService todoService;

    public IndexController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping({"","/"})
    public String getIndexPage(@ModelAttribute("project") String projectName,
                               @ModelAttribute("priority") String priority,
                               @ModelAttribute("status") String status,
                               @ModelAttribute("username") String username,
                               Model model) {
        log.debug("Index Page Requested.");

        model.addAttribute("todo_list", todoService.findAll().stream()
                .filter(todo -> projectName.isEmpty() || projectName.toLowerCase().equals(
                        todo.getProject().getName().toLowerCase()))
                .filter(todo -> priority.isEmpty() || priority.toUpperCase().equals(todo.getPriority().name()))
                .filter(todo -> status.isEmpty() || status.toUpperCase().equals(todo.getStatus().name()))
                .filter(todo -> username.isEmpty() || username.toLowerCase().equals(
                        Optional.ofNullable(todo.getAssignedUser()).orElse(User.builder().username("").build()).getUsername().toLowerCase()))
                .collect(Collectors.toList()));

        return "index";
    }
}
