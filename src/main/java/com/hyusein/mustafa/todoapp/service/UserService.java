package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.customexception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.Authority;
import com.hyusein.mustafa.todoapp.model.AuthorityType;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.AuthorityRepository;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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

        Authority authority = authorityRepository.findByName(AuthorityType.USER);
        if(authority != null) {
            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.getAuthorities().add(authority);
            return userRepository.save(newUser);
        }
        throw new UserAlreadyExistException(
                "The account could not create. Server error."
        );
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
