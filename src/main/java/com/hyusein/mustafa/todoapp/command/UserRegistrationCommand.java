package com.hyusein.mustafa.todoapp.command;

import com.hyusein.mustafa.todoapp.customannotation.PasswordMatches;
import com.hyusein.mustafa.todoapp.customannotation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches(message = "Passwords do not match")
public class UserRegistrationCommand {
    @NotNull(message = "First Name must not be empty")
    @NotEmpty(message = "First Name must not be empty")
    private String firstName;

    @NotNull(message = "Last Name must not be empty")
    @NotEmpty(message = "Last Name must not be empty")
    private String lastName;

    @NotNull(message = "Username must not be empty")
    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotNull(message = "Password must not be empty")
    @NotEmpty(message = "Password must not be empty")
    private String password;
    private String matchingPassword;

    @ValidEmail
    private String email;
}
