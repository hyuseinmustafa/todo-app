package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.CommentCommand;
import com.hyusein.mustafa.todoapp.model.Comment;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.service.CommentService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class CommentControllerTest {

    private String TOKEN_ATTR_NAME;
    CsrfToken csrfToken;

    @MockBean
    CommentService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        csrfToken = new HttpSessionCsrfTokenRepository().generateToken(new MockHttpServletRequest());
    }

    @Test
    void getViewPage() throws Exception {
        Set<Comment> comments = new HashSet<>();
        comments.add(Comment.builder()
                .id(1L)
                .comment("test")
                .user(User.builder().build())
                .todo(Todo.builder().build())
                .build());

        when(service.findAllByTodoId(anyLong())).thenReturn(comments);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment_list"))
                .andExpect(model().attributeExists("todoId"))
                .andExpect(view().name("comment/view"));
    }

    @Test
    void getNewCommentPage() throws Exception {
        mockMvc.perform(get("/comments/1/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("new_comment"))
                .andExpect(view().name("comment/new"));
    }

    @Test
    void getEditCommentPage() throws Exception {
        mockMvc.perform(get("/comments/edit/1"))
                .andExpect(status().is3xxRedirection());

        Comment comment = Comment.builder()
                .id(1L)
                .comment("test comment")
                .todo(Todo.builder().build())
                .user(User.builder().build())
                .build();

        when(service.findById(anyLong())).thenReturn(comment);

        mockMvc.perform(get("/comments/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("new_comment"))
                .andExpect(view().name("comment/new"));
    }

    @Test
    void saveNewComment() throws Exception {
        CommentCommand commentCommand = CommentCommand.builder().build();

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/comments/save", commentCommand)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/new"));

        commentCommand.setComment("test comment");
        commentCommand.setTodoId(1L);

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/comments/save", commentCommand)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        when(service.save(any())).thenReturn(Comment.builder().build());

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/comments/save", commentCommand)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comments/1"));
    }
}