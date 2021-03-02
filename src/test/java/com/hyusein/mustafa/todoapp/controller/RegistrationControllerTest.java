package com.hyusein.mustafa.todoapp.controller;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.exception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.service.ProjectService;
import com.hyusein.mustafa.todoapp.service.UserService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    private String TOKEN_ATTR_NAME;
    CsrfToken csrfToken;

    @MockBean
    UserService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        csrfToken = new HttpSessionCsrfTokenRepository().generateToken(new MockHttpServletRequest());
    }

    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("registration"));
    }

    @Test
    void registerUserAccount() throws Exception {
        UserRegistrationCommand user = UserRegistrationCommand.builder()
                .firstName("first Name")
                .lastName("last Name")
                .username("testuser")
                .email("asd@asd.com")
                .password("asdasdasd")
                .matchingPassword("asdasdasd")
                .build();

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/registration", user)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        user.setUsername("");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/registration", user)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));

        when(service.registerNewUserAccount(Mockito.any())).thenThrow(new UserAlreadyExistException("message"));

        user.setUsername("asd");
        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/registration", user)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}