package com.hyusein.mustafa.todoapp.command;

import com.hyusein.mustafa.todoapp.annotation.PasswordMatches;
import com.hyusein.mustafa.todoapp.annotation.ValidEmail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches(message = "Passwords do not match")
public class UserRegistrationCommand {
    
    @NotBlank(message = "First Name must not be empty")
    @Size(min = 2, max = 50, message = "First Name is too short ")
    private String firstName;

    @NotBlank(message = "Last Name must not be empty")
    @Size(min = 2, max = 50, message = "Last Name is too short ")
    private String lastName;

    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 50, message = "username is too short ")
    private String username;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, max = 50, message = "Password is too short ")
    private String password;
    private String matchingPassword;

    @ValidEmail
    private String email;

    @Builder
    public UserRegistrationCommand(String firstName, String lastName, String username,
                                   String password, String matchingPassword, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }
}
