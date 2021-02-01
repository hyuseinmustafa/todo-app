package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
