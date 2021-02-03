package com.hyusein.mustafa.todoapp.model;

public enum AuthorityType {
    ADMIN("Admin"),
    USER("User");

    private final String value;

    AuthorityType(String value) {
        this.value = value;
    }
}
