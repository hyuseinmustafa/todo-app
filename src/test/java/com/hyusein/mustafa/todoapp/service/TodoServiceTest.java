package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.enums.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    UserService userService;

    TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoServiceImpl(todoRepository, userService);

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
    void saveCommand() {
        TodoCommand todoCommand = TodoCommand.builder()
                .id(1L)
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

        Todo res = todoService.saveCommand(todoCommand);

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
    void doneByAuthority() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("FINISH_TODO"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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

    @Test
    void doneByUser() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Optional<Todo> todo = Optional.ofNullable(
                Todo.builder()
                        .id(1L)
                        .headline("headline")
                        .description("description")
                        .status(ToDoStatus.WAITING)
                        .project(Project.builder().id(2L).name("name").build())
                        .assignedUser(User.builder().username("asd").build())
                        .build()
        );

        when(todoRepository.findById(Mockito.anyLong())).thenReturn(todo);
        when(todoRepository.save(Mockito.any())).thenReturn(todo.get());

        todoService.done(1L);

        verify(todoRepository, times(1)).findById(Mockito.anyLong());
        verify(todoRepository, times(1)).save(Mockito.any());
    }

    @Test
    void doneFailure() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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

        todoService.done(1L);

        verify(todoRepository, times(1)).findById(Mockito.anyLong());
        verify(todoRepository, times(0)).save(Mockito.any());
    }

    @Test
    void assignUser() {
        Optional<Todo> todo = Optional.ofNullable(
                Todo.builder()
                        .id(1L)
                        .headline("headline")
                        .description("description")
                        .status(ToDoStatus.WAITING)
                        .project(Project.builder().id(2L).name("name").build())
                        .build()
        );

        when(todoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(todoRepository.save(Mockito.any())).thenReturn(todo.get());

        todoService.assignUser(1L, "asd");

        when(todoRepository.findById(Mockito.anyLong())).thenReturn(todo);

        todoService.assignUser(1L, "asd");

        todo.get().setStatus(ToDoStatus.FINISHED);
        when(todoRepository.findById(Mockito.anyLong())).thenReturn(todo);

        todoService.assignUser(1L, "asd");

        verify(todoRepository, times(3)).findById(Mockito.anyLong());
        verify(todoRepository, times(1)).save(Mockito.any());

    }
}