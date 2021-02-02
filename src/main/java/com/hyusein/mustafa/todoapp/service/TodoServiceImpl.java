package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.converter.TodoToTodoCommandConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService{
    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TodoCommand> findAll() {
        List<TodoCommand> list = new ArrayList<>();
        repository.findAll().forEach(value ->
                list.add(new TodoToTodoCommandConverter().convert(value)));
        return list;
    }

    @Override
    public TodoCommand findById(Long id) {
        return new TodoToTodoCommandConverter().convert(repository.findById(id).orElse(null));
    }

    @Override
    public TodoCommand save(TodoCommand todoCommand) {
        return new TodoToTodoCommandConverter().convert(
                repository.save(new TodoCommandToTodoConverter().convert(todoCommand))
        );
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void done(Long id) {
        Optional<Todo> todoOptional = repository.findById(id);
        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            todo.setStatus(ToDoStatus.FINISHED);
            repository.save(todo);
        }
    }
}
