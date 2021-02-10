package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.exception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.RoleRepository;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserRegistrationCommand user) throws UserAlreadyExistException {
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  user.getEmail());
        }

        if (usernameExist(user.getUsername())) {
            throw new UserAlreadyExistException(
                    "There is an account with that username: "
                            +  user.getUsername());
        }

        Role role = roleRepository.findByName("ROLE_USER");
        if(role != null) {
            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setEnabled(true);
            newUser.setRoles(Arrays.asList(role));
            return userRepository.save(newUser);
        }
        throw new UserAlreadyExistException(
                "The account could not create. Server error."
        );
    }

    @Override
    public Set<String> findAllUsernames() {
        Set<String> usernames = new HashSet<>();
        userRepository.findAll().forEach(val -> usernames.add(val.getUsername()));
        if(usernames.size() > 0)
            return usernames;
        return null;
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
