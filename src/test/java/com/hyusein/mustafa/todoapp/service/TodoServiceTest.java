package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoServiceImpl(todoRepository);
    }

    @Test
    void findAll() {
        List<Todo> todo = new ArrayList<>();
        todo.add(Todo.builder()
                        .id(1L)
                        .headline("headline")
                        .description("description")
                        .status(ToDoStatus.WAITING)
                        .project(Project.builder().id(2L).name("name").todos(new HashSet<>()).build())
                        .build()
        );

        when(todoRepository.findAll()).thenReturn(todo);

        List<Todo> res = todoService.findAll();

        verify(todoRepository, times(1)).findAll();

        assertEquals(res.get(0).getId(), todo.get(0).getId());
        assertTrue(res.get(0).getHeadline().equals(todo.get(0).getHeadline()));
        assertTrue(res.get(0).getDescription().equals(todo.get(0).getDescription()));
        assertTrue(res.get(0).getStatus().equals(todo.get(0).getStatus()));
        assertEquals(res.get(0).getProject().getId(), todo.get(0).getProject().getId());
        assertTrue(res.get(0).getProject().getName().equals(todo.get(0).getProject().getName()));
    }

    @Test
    void findById() {
        Optional<Todo> todo = Optional.ofNullable(
                Todo.builder()
                        .id(1L)
                        .headline("headline")
                        .description("description")
                        .status(ToDoStatus.WAITING)
                        .project(Project.builder().id(2L).name("name").todos(new HashSet<>()).build())
                        .build()
        );

        when(todoRepository.findById(Mockito.anyLong())).thenReturn(todo);

        Todo res = todoService.findById(1L);

        verify(todoRepository, times(1)).findById(Mockito.anyLong());

        assertEquals(res.getId(), todo.get().getId());
        assertTrue(res.getHeadline().equals(todo.get().getHeadline()));
        assertTrue(res.getDescription().equals(todo.get().getDescription()));
        assertTrue(res.getStatus().equals(todo.get().getStatus()));
        assertEquals(res.getProject().getId(), todo.get().getProject().getId());
        assertTrue(res.getProject().getName().equals(todo.get().getProject().getName()));
    }

    @Test
    void save() {
        TodoCommand todoCommand = TodoCommand.builder()
                .headline("headline1")
                .description("description1")
                .status(ToDoStatus.FINISHED)
                .project(ProjectCommand.builder().id(3L).name("name1").build())
                .build();

        Project project = Project.builder().id(2L).name("name").todos(new HashSet<>()).build();
        Todo todo = Todo.builder()
                .id(1L)
                .headline("headline")
                .description("description")
                .status(ToDoStatus.WAITING)
                .project(project)
                .build();

        when(todoRepository.save(Mockito.any())).thenReturn(todo);

        Todo res = todoService.save(todoCommand);

        verify(todoRepository, times(1)).save(Mockito.any());

        assertEquals(res.getId(), todo.getId());
        assertTrue(res.getHeadline().equals(todo.getHeadline()));
        assertTrue(res.getDescription().equals(todo.getDescription()));
        assertTrue(res.getStatus().equals(todo.getStatus()));
        assertEquals(res.getProject().getId(), todo.getProject().getId());
        assertTrue(res.getProject().getName().equals(todo.getProject().getName()));
    }

    @Test
    void deleteById() {
        todoService.deleteById(1L);

        verify(todoRepository, times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void done() {
        Optional<Todo> todo = Optional.ofNullable(
                Todo.builder()
                        .id(1L)
                        .headline("headline")
                        .description("description")
                        .status(ToDoStatus.WAITING)
                        .project(Project.builder().id(2L).name("name").build())
                        .build()
        );

        when(todoRepository.findById(Mockito.anyLong())).thenReturn(todo);
        when(todoRepository.save(Mockito.any())).thenReturn(todo.get());

        todoService.done(1L);

        verify(todoRepository, times(1)).findById(Mockito.anyLong());
        verify(todoRepository, times(1)).save(Mockito.any());
    }
}