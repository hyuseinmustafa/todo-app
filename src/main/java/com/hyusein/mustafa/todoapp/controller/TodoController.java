package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.AssignCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoToTodoCommandConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import com.hyusein.mustafa.todoapp.service.TodoService;
import com.hyusein.mustafa.todoapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserService userService;

    public TodoController(TodoService todoService, ProjectService projectService, UserService userService) {
        this.todoService = todoService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        //dataBinder.setDisallowedFields("id");
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @GetMapping({"/new"})
    public String getNewToDoPage(Model model){
        log.debug("Add new ToDo Page Requested.");

        model.addAttribute("neworedit_todo", new TodoCommand());
        model.addAttribute("project_list", projectService.findAllAsCommand());

        return "todo/new";
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @GetMapping("/{id}/edit")
    public String getEditToDoPage(@PathVariable("id") Long id, Model model){
        log.debug("Edit Todo.");

        model.addAttribute("neworedit_todo", new TodoToTodoCommandConverter().convert(todoService.findById(id)));
        model.addAttribute("project_list", projectService.findAllAsCommand());

        return "todo/new";
    }

    @GetMapping("/{id}/done")
    public String getDoneToDoDoneButton(@PathVariable("id") Long id, Model model){
        log.debug("Done Todo. ID: " + id);

        todoService.done(id);

        return "redirect:/";
    }

    @PreAuthorize("(hasAuthority('EDIT') and #todo.id != null) or (hasAuthority('CREATE') and #todo.id == null)")
    @PostMapping({"/save"})
    @Transactional
    public String saveNewToDoPage(@Valid @ModelAttribute("neworedit_todo") TodoCommand todo, BindingResult result, Model model){
        if(result.hasErrors()){
            log.debug("New ToDo page post Validation error.");

            model.addAttribute("project_list", projectService.findAllAsCommand());

            return "todo/new";
        }

        Todo savedTodo = todoService.saveCommand(todo);

        log.debug("ToDo saved id: " + savedTodo.getId());

        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @GetMapping("/{id}/delete")
    public String deleteToDoPage(@PathVariable("id") Long id){
        log.debug("ToDo delete. id: " + id);

        todoService.deleteById(id);

        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ASSIGN')")
    @GetMapping("/{id}/assign")
    public String assignUserPageRequest(@PathVariable("id") Long id, Model model){
        log.debug("Todo assign page requested. id: " + id);

        AssignCommand assignCommand = new AssignCommand();
        assignCommand.setTodoId(id);
        assignCommand.setUserList(userService.findAllUsernames());
        model.addAttribute("assignCommand", assignCommand);

        return "todo/assign";
    }

    @PreAuthorize("hasAuthority('ASSIGN')")
    @PostMapping("/assign")
    public String assignUserPageSave(@Valid @ModelAttribute("assignCommand") AssignCommand assignCommand){
        log.debug("assignUserPageSave. id: " + assignCommand.getTodoId());

        Todo todo = todoService.assignUser(assignCommand.getTodoId(), assignCommand.getAssignedUser());
        if(todo != null)
            return "redirect:/";

        return "todo/assign";
    }
}
