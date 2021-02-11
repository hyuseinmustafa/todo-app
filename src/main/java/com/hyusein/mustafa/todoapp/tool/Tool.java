package com.hyusein.mustafa.todoapp.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface Tool {
    Logger log = LoggerFactory.getLogger(Tool.class);

    /*
     * Finds properties of Class if getter and setter available
     * then null org properties copied from src properties
     *
     * getter starts "get" and continues camelcase property
     * setter starts "set" and continues camelcase property
     *
     * example : getProperty, setProperty
     */
    static <T> T remediate(T org, T src) {
        if(src != null) for(Field field : org.getClass().getDeclaredFields()){
            String getter = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
            String setter = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
            try{
                Method get = src.getClass().getMethod(getter);
                Method set = org.getClass().getMethod(setter, field.getType());
                if(get.invoke(org) == null)set.invoke(org, get.invoke(src));
            }catch (NoSuchMethodException e){
                log.warn("getter or/and setter could not find for property: " + field.getName());
            }catch (Exception e){
                log.error(e.toString());
            }
        }
        return org;
    }
}
