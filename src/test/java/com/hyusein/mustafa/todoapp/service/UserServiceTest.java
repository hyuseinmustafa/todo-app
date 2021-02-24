package com.hyusein.mustafa.todoapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void isAuthenticated() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken("asd","asd",authorities);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        assertTrue(UserService.isAuthenticated());

        authentication = null;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        assertFalse(UserService.isAuthenticated());
    }
}