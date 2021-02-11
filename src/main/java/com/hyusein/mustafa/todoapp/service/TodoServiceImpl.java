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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
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

    @Transactional
    @Override
    public void done(Long id) {
        todoRepository.findById(id).ifPresent(todo -> {
            todo.setStatus(ToDoStatus.FINISHED);
            todoRepository.save(todo);
        });
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
