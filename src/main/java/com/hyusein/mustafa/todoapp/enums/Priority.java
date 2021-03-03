package com.hyusein.mustafa.todoapp.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String value;

    Priority(String value) {
        this.value = value;
    }
}
