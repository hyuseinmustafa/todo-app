package com.hyusein.mustafa.todoapp.annotation;

import com.hyusein.mustafa.todoapp.command.UserRegistrationCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegistrationCommand user = (UserRegistrationCommand) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
