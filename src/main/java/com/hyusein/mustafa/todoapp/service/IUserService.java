package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;
import com.hyusein.mustafa.todoapp.customexception.UserAlreadyExistException;
import com.hyusein.mustafa.todoapp.model.User;

public interface IUserService {
    User registerNewUserAccount(UserRegistrationCommand userDto)
            throws UserAlreadyExistException;
}
