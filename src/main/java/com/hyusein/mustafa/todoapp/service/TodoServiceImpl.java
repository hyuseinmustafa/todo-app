package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.enums.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserService userService;

    public TodoServiceImpl(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    @Override
    public List<Todo> findAll() {
        return StreamSupport.stream(todoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo saveCommand(TodoCommand todoCommand) {
        return save(new TodoCommandToTodoConverter().convert(todoCommand).ifIdPresentRemediate(todo ->
                this.findById(todo.getId())));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void done(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        todoRepository.findById(id)
                .filter(val -> {
                    if(authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).anyMatch("FINISH_TODO"::equals)) return true;
                    if(val.getAssignedUser() != null)
                        if(val.getAssignedUser().getUsername().equals(
                                authentication.getName()
                        )) return true;
                    return false;
                })
                .ifPresent(todo -> {
                    todo.setStatus(ToDoStatus.FINISHED);
                    todo.setDoneBy(userService.findByUsername(authentication.getName()));
                    this.save(todo);
                });
    }

    @Override
    public Todo assignUser(Long todoId, String username) {
        Todo todo = findById(todoId);
        if(todo != null) {
            if(todo.getStatus() != ToDoStatus.FINISHED) {
                todo.setAssignedUser(userService.findByUsername(username));
                return this.save(todo);
            }
        }
        return null;
    }
}
