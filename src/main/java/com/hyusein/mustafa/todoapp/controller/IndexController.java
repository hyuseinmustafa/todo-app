package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final TodoRepository todoRepository;

    public IndexController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping({"","/"})
    public String getIndexPage(Model model) {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        model.addAttribute("todo_list", todos);

        return "index";
    }
}
