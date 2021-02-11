package com.hyusein.mustafa.todoapp.tool;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public interface Tool {
    @SneakyThrows
    static void remediate(Object org, Object src) {
        if(src != null) for(Field field : org.getClass().getDeclaredFields()){
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if(field.get(org) == null)field.set(org, field.get(src));
            field.setAccessible(isAccessible);
        }
    }
}
