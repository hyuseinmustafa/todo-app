package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.exception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public interface UserService {
    User registerNewUserAccount(UserRegistrationCommand userDto) throws UserAlreadyExistException;
    Set<String> findAllUsernames();

    /*
     * This method checks is user logged in
     */
    static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
