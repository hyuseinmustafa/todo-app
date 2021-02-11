package com.hyusein.mustafa.todoapp.tool;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public interface Tool<T> {
    T andRemediate(T src);

    @SneakyThrows
    default T remediate(T org, T src) {
        if(src != null) for(Field field : org.getClass().getDeclaredFields()){
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            if(field.get(org) == null)field.set(org, field.get(src));
            field.setAccessible(isAccessible);
        }
        return org;
    }
}
