package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> findAll();
    Todo findById(Long id);
    Todo save(TodoCommand todoCommand);
    void deleteById(Long id);
    void done(Long id);
}
