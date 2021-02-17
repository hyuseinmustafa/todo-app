package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import com.hyusein.mustafa.todoapp.service.ProjectService;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(ProjectController.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectControllerTest {

    private String TOKEN_ATTR_NAME;
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository;
    CsrfToken csrfToken;

    @MockBean
    ProjectService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    }

    @Test
    void getProjectsPage() throws Exception {
        List<Project> list = new ArrayList<>();
        list.add(Project.builder().id(1L).name("test").todos(new HashSet<>()).build());

        when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/project"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("project_list", list))
                .andExpect(view().name("project/list"));

        verify(service, times(1)).findAll();
    }

    @Test
    @WithMockUser(authorities = {"CREATE_PROJECT"})
    void getNewProjectPage() throws Exception {
        mockMvc.perform(get("/project/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("neworedit_project"))
                .andExpect(view().name("project/new"));

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = {"EDIT_PROJECT"})
    void getEditProjectPage() throws Exception {
        Project project = Project.builder().id(1L).name("test").build();

        when(service.findById(Mockito.anyLong())).thenReturn(project);

        mockMvc.perform(get("/project/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("neworedit_project", hasProperty("id")))
                .andExpect(model().attribute("neworedit_project", hasProperty("name")))
                .andExpect(view().name("project/new"));

        verify(service, times(1)).findById(Mockito.anyLong());
    }

    @Test
    @WithMockUser(authorities = {"CREATE_PROJECT"})
    void saveNewProjectPage() throws Exception {
        Project project = Project.builder().id(1L).name("test").build();

        when(service.save(Mockito.any())).thenReturn(project);

        mockMvc.perform(post("/project/save").param("name", "test")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/project"));

        verify(service, times(1)).save(Mockito.any(ProjectCommand.class));
    }

    @Test
    @WithMockUser(authorities = {"DELETE_PROJECT"})
    void deleteProjectPage() throws Exception {
        mockMvc.perform(get("/project/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/project"));

        verify(service, times(1)).deleteById(Mockito.anyLong());
    }
}