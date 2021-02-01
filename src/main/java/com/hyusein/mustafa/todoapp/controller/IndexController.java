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
public class IndexController {

    private final TodoRepository todoRepository;

    public IndexController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping({"","/"})
    public String getIndexPage(Model model) {
        log.debug("Index Page Requested.");

        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        model.addAttribute("todo_list", todos);

        return "index";
    }
}
