package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @MockBean
    TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getIndexPage() throws Exception {
        List<TodoCommand> list = new ArrayList<>();
        ProjectCommand project = ProjectCommand.builder().id(1L).name("test").build();
        list.add(TodoCommand.builder().id(1L).project(project).status(ToDoStatus.WAITING).build());

        when(todoService.findAll()).thenReturn(list);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("todo_list", list))
                .andExpect(view().name("index"));
    }
}