package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import com.hyusein.mustafa.todoapp.repository.TodoRepository;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import com.hyusein.mustafa.todoapp.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @MockBean
    TodoService todoService;

    @MockBean
    ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getNewToDoPage() throws Exception {
        List<ProjectCommand> list = new ArrayList<>();
        list.add(ProjectCommand.builder().id(1L).name("test").build());

        when(projectService.findAll()).thenReturn(list);

        mockMvc.perform(get("/todo/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("neworedit_todo", hasProperty("project")))
                .andExpect(view().name("todo/new"));

        verify(projectService, times(1)).findAll();
        verifyNoInteractions(todoService);
    }
    @Test
    void getEditToDoPage() throws Exception {
        ProjectCommand project =  ProjectCommand.builder().id(1L).name("test").build();
        TodoCommand todo = TodoCommand.builder()
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

        verify(projectService, times(1)).findAll();
        verify(todoService, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void getDoneToDoDoneButton() throws Exception {
        ProjectCommand project =  ProjectCommand.builder().id(1L).name("test").build();
        TodoCommand todo = TodoCommand.builder()
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

    @Test
    void saveNewToDoPage() throws Exception {

//        mockMvc.perform(post("/todo/save")
//                    .param("headline", "test headline")
//                    .param("status", "WAITING")
//                    .param("project", "1")
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/project"));
//
//        verify(todoService, times(1)).save(Mockito.any(TodoCommand.class));
    }

    @Test
    void deleteToDoPage() throws Exception {
        mockMvc.perform(get("/todo/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(todoService, times(1)).deleteById(Mockito.anyLong());
    }
}