package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class AdminPageControllerTest {

    private String TOKEN_ATTR_NAME;
    CsrfToken csrfToken;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        csrfToken = new HttpSessionCsrfTokenRepository().generateToken(new MockHttpServletRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allPrivileges"))
                .andExpect(model().attributeExists("allRoles"))
                .andExpect(model().attributeDoesNotExist("role"))
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(view().name("adminpanel/view"));

        mockMvc.perform(get("/admin/role/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allPrivileges"))
                .andExpect(model().attributeExists("allRoles"))
                .andExpect(model().attributeExists("role"))
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(view().name("adminpanel/view"));

        mockMvc.perform(get("/admin/user?username=asd"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allPrivileges"))
                .andExpect(model().attributeExists("allRoles"))
                .andExpect(model().attributeDoesNotExist("role"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("adminpanel/view"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void savePrivileges() throws Exception {
        Role role = Role.builder().id(1L).name("TEST").build();

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/admin/save/1", role)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void saveUserRole() throws Exception {
        User user = User.builder().build();

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/admin/user/save", user)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));
    }
}