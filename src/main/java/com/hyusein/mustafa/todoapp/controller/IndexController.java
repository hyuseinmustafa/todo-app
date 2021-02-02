package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class IndexController {

    private final TodoService todoService;

    public IndexController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping({"","/"})
    public String getIndexPage(Model model) {
        log.debug("Index Page Requested.");

        model.addAttribute("todo_list", todoService.findAll());

        return "index";
    }
}
