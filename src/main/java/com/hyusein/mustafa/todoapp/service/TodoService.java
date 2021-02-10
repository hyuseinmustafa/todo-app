package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> findAll();
    Todo findById(Long id);
    Todo save(Todo todo);
    Todo saveCommand(TodoCommand todoCommand);
    void deleteById(Long id);
    void done(Long id);
    Todo assignUser(Long todoId, String username);
}
