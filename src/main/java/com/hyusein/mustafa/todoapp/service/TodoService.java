package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.TodoCommand;

import java.util.List;

public interface TodoService {
    List<TodoCommand> findAll();
    TodoCommand findById(Long id);
    TodoCommand save(TodoCommand todoCommand);
    void deleteById(Long id);
    void done(Long id);
}
