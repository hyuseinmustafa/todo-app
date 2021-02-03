package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.command.TodoCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.model.Todo;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    private final static Long PROJECT_ID = 2L;
    private final static String PROJECT_NAME = "test prj";
    private final static Long TODO_ID = 1L;
    private final static String TODO_HEADLINE = "test headline";
    private final static String TODO_DESCRIPTION = "test description";
    private final static ToDoStatus TODO_STATUS = ToDoStatus.WAITING;
    private final static ProjectCommand TODO_PROJECT_COMMAND = new ProjectCommand().builder()
            .id(PROJECT_ID).name(PROJECT_NAME).build();
    private final static Project TODO_PROJECT = new Project().builder()
            .id(PROJECT_ID).name(PROJECT_NAME).todos(new HashSet<>()).build();

    @Test
    void ProjectCommandToProjectConvert() {
        ProjectCommand src = ProjectCommand.builder()
                .id(PROJECT_ID).name(PROJECT_NAME).build();
        Project trg = new ProjectCommandToProjectConverter().convert(src);

        assertNull(new ProjectCommandToProjectConverter().convert(null));
        assertNotNull(trg);
        assertEquals(src.getId(), trg.getId());
        assertTrue(src.getName().equals(trg.getName()));
    }

    @Test
    void ProjectToProjectCommandConvert() {
        Set<Todo> todos = new HashSet<>();
        todos.add(Todo.builder().build());

        Project src = Project.builder().id(PROJECT_ID).name(PROJECT_NAME).todos(todos).build();
        ProjectCommand trg = new ProjectToProjectCommandConverter().convert(src);

        assertNull(new ProjectToProjectCommandConverter().convert(null));
        assertNotNull(trg);
        assertEquals(src.getId(), trg.getId());
        assertTrue(src.getName().equals(trg.getName()));
    }

    @Test
    void TodoCommandToTodoConvert() {
        TodoCommand src = TodoCommand.builder()
                .id(TODO_ID)
                .headline(TODO_HEADLINE)
                .description(TODO_DESCRIPTION)
                .status(TODO_STATUS)
                .project(TODO_PROJECT_COMMAND)
                .build();
        Todo trg = new TodoCommandToTodoConverter().convert(src);

        assertNull(new TodoCommandToTodoConverter().convert(null));
        assertNotNull(trg);
        assertEquals(src.getId(), trg.getId());
        assertEquals(src.getHeadline(), trg.getHeadline());
        assertEquals(src.getDescription(), trg.getDescription());
        assertEquals(src.getStatus(), trg.getStatus());
        assertEquals(src.getProject().getId(), trg.getProject().getId());
        assertEquals(src.getProject().getName(), trg.getProject().getName());
    }

    @Test
    void TodoToTodoCommandConvert() {
        Todo src = Todo.builder()
                .id(TODO_ID)
                .headline(TODO_HEADLINE)
                .description(TODO_DESCRIPTION)
                .status(TODO_STATUS)
                .project(TODO_PROJECT)
                .build();
        TodoCommand trg = new TodoToTodoCommandConverter().convert(src);

        assertNull(new TodoToTodoCommandConverter().convert(null));
        assertNotNull(trg);
        assertEquals(src.getId(), trg.getId());
        assertEquals(src.getHeadline(), trg.getHeadline());
        assertEquals(src.getDescription(), trg.getDescription());
        assertEquals(src.getStatus(), trg.getStatus());
        assertEquals(src.getProject().getId(), trg.getProject().getId());
        assertEquals(src.getProject().getName(), trg.getProject().getName());
    }
}