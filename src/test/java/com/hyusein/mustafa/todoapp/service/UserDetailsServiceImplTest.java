package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.model.Privilege;
import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class UserDetailsServiceImplTest {

    @MockBean
    UserRepository userRepository;

    UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        userDetailsService = new UserDetailsServiceImpl();
        ReflectionTestUtils.setField(userDetailsService, "userRepository", userRepository);
    }

    @Test
    void loadUserByUsername() {
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(Privilege.builder().id(1L).name("WRITE").build());

        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(1L).name("ADMIN").privileges(privileges).build());

        User user = User.builder()
                .id(1L)
                .email("test@asd.com")
                .username("test")
                .password("password")
                .enabled(true)
                .roles(roles)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,() -> {
            userDetailsService.loadUserByUsername("test");
        });

        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");
    }
}