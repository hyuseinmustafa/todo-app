package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import com.hyusein.mustafa.todoapp.service.TodoService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(TodoController.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class TodoControllerTest {

    private String TOKEN_ATTR_NAME;
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
    CsrfToken csrfToken;

    @MockBean
    TodoService todoService;

    @MockBean
    ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    }

    @Test
    void getNewToDoPage() throws Exception {
        List<ProjectCommand> list = new ArrayList<>();
        list.add(ProjectCommand.builder().id(1L).name("test").build());

        when(projectService.findAllAsCommand()).thenReturn(list);

        mockMvc.perform(get("/todo/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("neworedit_todo", hasProperty("project")))
                .andExpect(view().name("todo/new"));

        verify(projectService, times(1)).findAllAsCommand();
        verifyNoInteractions(todoService);
    }
    @Test
    void getEditToDoPage() throws Exception {
        Project project =  Project.builder().id(1L).name("test").build();
        Todo todo = Todo.builder()
                .id(1L)
                .headline("test headline")
                .status(ToDoStatus.WAITING)
                .project(project)
                .build();

        when(todoService.findById(Mockito.anyLong())).thenReturn(todo);

        mockMvc.perform(get("/todo/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("neworedit_todo", hasProperty("project")))
                .andExpect(view().name("todo/new"));

        verify(projectService, times(1)).findAllAsCommand();
        verify(todoService, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void getDoneToDoDoneButton() throws Exception {
        Project project =  Project.builder().id(1L).name("test").build();
        Todo todo = Todo.builder()
                .id(1L)
                .headline("test headline")
                .status(ToDoStatus.WAITING)
                .project(project)
                .build();

        when(todoService.findById(Mockito.anyLong())).thenReturn(todo);

        mockMvc.perform(get("/todo/1/done"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(todoService, times(1)).done(Mockito.anyLong());
    }

    /*
    * Regular MockMvc test gives an Type conversation error from String To TodoCommand object
    * To overcome this issue added dependency for "Spring MVC Test utils"
    * link: "https://github.com/f-lopes/spring-mvc-test-utils/"
    *
	*	<dependency>
	*		<groupId>io.florianlopes</groupId>
	*		<artifactId>spring-mvc-test-utils</artifactId>
	*		<version>2.3.0</version>
	*		<scope>test</scope>
	*	</dependency>
    *
     */
    @Test
    void saveNewToDoPage() throws Exception {
        Todo todo = Todo.builder()
                .id(1L)
                .headline("headline")
                .description("")
                .status(ToDoStatus.WAITING)
                .project(Project.builder().id(2L).name("name").build())
                .build();

        TodoCommand todoCommand = TodoCommand.builder()
                .id(1L)
                .headline("headline")
                .description("")
                .status(ToDoStatus.WAITING)
                .project(ProjectCommand.builder().id(2L).name("name").build())
                .build();

        when(todoService.save(Mockito.any())).thenReturn(todo);

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/todo/save", todoCommand)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(todoService, times(1)).save(Mockito.any(TodoCommand.class));
    }

    @Test
    void deleteToDoPage() throws Exception {
        mockMvc.perform(get("/todo/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(todoService, times(1)).deleteById(Mockito.anyLong());
    }
}