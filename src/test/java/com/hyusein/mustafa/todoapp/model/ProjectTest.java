package com.hyusein.mustafa.todoapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private final static Long ID = 1L;
    private final static String NAME = "name";
    private final static Todo TODO = Todo.builder().build();

    Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
    }

    @Test
    void getId() {
        project.setId(ID);

        assertEquals(project.getId(), ID);
    }

    @Test
    void getName() {
        project.setName(NAME);

        assertTrue(project.getName().equals(NAME));
    }

    @Test
    void getTodo() {
        project.setTodos(new HashSet<>());
        project.getTodos().add(TODO);

        assertTrue(project.getTodos().contains(TODO));
        assertEquals(project.getTodos().size(), 1);
    }
}