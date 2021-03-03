package com.hyusein.mustafa.todoapp;


public enum ToDoStatus {
    WAITING("Waiting"),
    FINISHED("Finished");

    private final String value;

    ToDoStatus(String value) {
        this.value = value;
    }

}
