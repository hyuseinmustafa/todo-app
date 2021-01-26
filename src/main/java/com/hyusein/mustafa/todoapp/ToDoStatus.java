package com.hyusein.mustafa.todoapp;



import java.util.HashMap;
import java.util.Map;

public enum ToDoStatus {
    WAITING("Waiting"),
    FINISHED("Finished");

    private final String value;

    ToDoStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
