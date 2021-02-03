package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
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
    private final ProjectRepository projectRepository;

    public TodoServiceImpl(TodoRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Todo> findAll() {
        List<Todo> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Todo findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Todo save(TodoCommand todoCommand) {
        return repository.save(new TodoCommandToTodoConverter().convert(todoCommand));
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
