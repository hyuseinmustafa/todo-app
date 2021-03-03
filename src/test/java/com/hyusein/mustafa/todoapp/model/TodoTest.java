package com.hyusein.mustafa.todoapp.model;

import com.hyusein.mustafa.todoapp.enums.ToDoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    private static final Long ID = 1L;
    private static final String HEADLINE = "headline";
    private static final String DESCRIPTION = "description";
    private static final ToDoStatus STATUS = ToDoStatus.WAITING;
    private static final Project PROJECT = new Project();

    Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
    }

    @Test
    void getId() {
        todo.setId(ID);

        assertEquals(todo.getId(), ID);
    }

    @Test
    void getHeadline() {
        todo.setHeadline(HEADLINE);

        assertTrue(todo.getHeadline().equals(HEADLINE));
    }

    @Test
    void getDescription() {
        todo.setDescription(DESCRIPTION);

        assertTrue(todo.getDescription().equals(DESCRIPTION));
    }

    @Test
    void getStatus() {
        todo.setStatus(STATUS);

        assertTrue(todo.getStatus().equals(STATUS));
    }

    @Test
    void getProject() {
        todo.setProject(PROJECT);

        assertEquals(todo.getProject(), PROJECT);
    }
}