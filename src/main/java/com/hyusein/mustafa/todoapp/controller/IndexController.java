package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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

    @GetMapping({"/new"})
    public String getNewToDoPage(Model model){
        log.debug("Add new ToDo Page Requested.");

        model.addAttribute("new_todo", new TodoCommand());

        return "new";
    }

    @PostMapping({"/new"})
    public String saveNewToDoPage(@Valid @ModelAttribute("new_todo") TodoCommand todo, BindingResult result){
        if(result.hasErrors()){
            log.debug("New ToDo page post Validation error.");

            return "new";
        }
        log.debug("New ToDo Added.");

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
