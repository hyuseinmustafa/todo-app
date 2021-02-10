package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.converter.TodoCommandToTodoConverter;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository repository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

    @Transactional
    @Override
    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public Todo saveCommand(TodoCommand todoCommand) {
        return save(new TodoCommandToTodoConverter().convert(todoCommand));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void done(Long id) {
        Optional<Todo> todoOptional = repository.findById(id);
        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            todo.setStatus(ToDoStatus.FINISHED);
            repository.save(todo);
        }
    }

    @Transactional
    @Override
    public Todo assignUser(Long todoId, String username) {
        Todo todo = findById(todoId);
        if(todo != null) {
            if(todo.getStatus() != ToDoStatus.FINISHED) {
                todo.setAssignedUser(userRepository.findByUsername(username));
                return save(todo);
            }
        }
        return null;
    }
}
