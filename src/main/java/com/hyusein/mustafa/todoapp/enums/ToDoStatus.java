package com.hyusein.mustafa.todoapp.enums;

import lombok.Getter;

@Getter
public enum ToDoStatus {
    WAITING("Waiting"),
    FINISHED("Finished");

    private final String value;

    ToDoStatus(String value) {
        this.value = value;
    }
}
