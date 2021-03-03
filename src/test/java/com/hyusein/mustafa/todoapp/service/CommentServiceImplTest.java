package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    TodoService todoService;

    @Mock
    UserService userService;

    @Mock
    CommentRepository commentRepository;

    CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository, todoService, userService);
    }

    @Test
    void findAllByTodoId() {
        Set<Comment> comments = new HashSet<>();
        comments.add(Comment.builder().build());
        comments.add(Comment.builder().build());

        when(commentRepository.findByTodo(any())).thenReturn(comments);

        Set<Comment> ret = commentService.findAllByTodoId(1L);

        assertEquals(2, ret.size());
    }

    @Test
    void findById() {
        Optional<Comment> comment = Optional.ofNullable(Comment.builder().build());

        when(commentRepository.findById(anyLong())).thenReturn(comment);

        Comment ret = commentService.findById(1L);

        assertEquals(comment.get(), ret);
    }

    @Test
    void saveNew() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("EDIT_COMMENT"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String COMMENT = "Test comment";
        Todo todo = Todo.builder().build();
        User user = User.builder().username("xxx").build();
        CommentCommand commentCommand = CommentCommand.builder()
                .todoId(1L)
                .comment(COMMENT)
                .build();

        when(todoService.findById(anyLong())).thenReturn(todo);
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment ret = commentService.save(commentCommand);

        assertEquals(todo, ret.getTodo());
        assertEquals(user, ret.getUser());
        assertEquals(COMMENT, ret.getComment());
    }

    @Test
    void saveEditByUsername() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String COMMENT = "Test comment";
        Todo todo = Todo.builder().build();
        User user = User.builder().username("asd").build();
        CommentCommand commentCommand = CommentCommand.builder()
                .id(1L)
                .todoId(1L)
                .comment(COMMENT)
                .build();

        when(todoService.findById(anyLong())).thenReturn(todo);
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(commentRepository.findById(anyLong())).thenReturn(
                Optional.ofNullable(Comment.builder()
                        .todo(todo)
                        .user(user)
                        .build())
        );

        Comment ret = commentService.save(commentCommand);

        assertEquals(todo, ret.getTodo());
        assertEquals(user, ret.getUser());
        assertEquals(COMMENT, ret.getComment());
    }

    @Test
    void saveEditByAuthority() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("EDIT_COMMENT"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String COMMENT = "Test comment";
        Todo todo = Todo.builder().build();
        User user = User.builder().username("xxx").build();
        CommentCommand commentCommand = CommentCommand.builder()
                .id(1L)
                .todoId(1L)
                .comment(COMMENT)
                .build();

        when(todoService.findById(anyLong())).thenReturn(todo);
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(commentRepository.findById(anyLong())).thenReturn(
                Optional.ofNullable(Comment.builder()
                        .todo(todo)
                        .user(user)
                        .build())
        );

        Comment ret = commentService.save(commentCommand);

        assertEquals(todo, ret.getTodo());
        assertEquals(user, ret.getUser());
        assertEquals(COMMENT, ret.getComment());
    }
}