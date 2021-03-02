package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.exception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.RoleRepository;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
    }

    @Test
    void findByUsername() {
        User user = User.builder().build();

        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);

        User foundUser = userService.findByUsername("test");

        assertEquals(foundUser, user);
    }

    @Test
    void findByEmail() {
        User user = User.builder().build();

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);

        User foundUser = userService.findByEmail("test");

        assertEquals(foundUser, user);
    }

    @Test
    void registerNewUserAccount() {
        User user = User.builder().build();
        Role role = Role.builder().id(1L).name("ROLE_USER").build();

        UserRegistrationCommand userRegistrationCommand = UserRegistrationCommand.builder()
                .username("test")
                .email("test@asd.com")
                .firstName("first Name")
                .lastName("last Name")
                .password("12341234")
                .matchingPassword("12341234")
                .build();

        //testing create user
        when(roleRepository.findByName(Mockito.anyString())).thenReturn(role);
        when(userRepository.save(Mockito.any())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        User savedUser = null;
        try {
            savedUser = userService.registerNewUserAccount(userRegistrationCommand);
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
        }

        assertEquals(savedUser, user);

        //testing role not found
        when(roleRepository.findByName(Mockito.anyString())).thenReturn(null);
        assertThrows(UserAlreadyExistException.class,() -> {
            userService.registerNewUserAccount(userRegistrationCommand);
        });

        //testing username exist
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        assertThrows(UserAlreadyExistException.class,() -> {
            userService.registerNewUserAccount(userRegistrationCommand);
        });

        //testing email exist
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(user);
        assertThrows(UserAlreadyExistException.class,() -> {
            userService.registerNewUserAccount(userRegistrationCommand);
        });

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void findAllUsernames() {
        Set<User> users = new HashSet<>();
        users.add(User.builder().username("user 1").build());
        users.add(User.builder().username("user 2").build());

        when(userRepository.findAll()).thenReturn(users);

        Set<String> foundUsernames = userService.findAllUsernames();

        assertEquals(foundUsernames.size(), 2);
    }

    @Test
    void saveUserRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().id(1L).name("Role").build());
        User user = User.builder().build();

        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        when(userRepository.save(Mockito.any())).thenReturn(user);

        userService.saveUserRole("test", roles);

        verify(userRepository, times(1)).save(any());
    }
}