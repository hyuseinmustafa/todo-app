package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectServiceImp(projectRepository);
    }

    @Test
    void findAll() {
        List<Project> list = new ArrayList();
        list.add(Project.builder().id(1L).name("test1 name").todos(new HashSet<>()).build());
        list.add(Project.builder().id(2L).name("test2 name").todos(new HashSet<>()).build());

        when(projectRepository.findAll()).thenReturn(list);

        List<Project> res = projectService.findAll();

        verify(projectRepository, times(1)).findAll();

        assertEquals(res.size(), list.size());
        assertEquals(res.get(0).getId(), list.get(0).getId());
        assertTrue(res.get(0).getName().equals(list.get(0).getName()));
    }

    @Test
    void findAllAsCommand() {
        List<Project> list = new ArrayList();
        list.add(Project.builder().id(1L).name("test1 name").todos(new HashSet<>()).build());
        list.add(Project.builder().id(2L).name("test2 name").todos(new HashSet<>()).build());

        when(projectRepository.findAll()).thenReturn(list);

        List<ProjectCommand> res = projectService.findAllAsCommand();

        verify(projectRepository, times(1)).findAll();

        assertEquals(res.size(), list.size());
        assertEquals(res.get(0).getId(), list.get(0).getId());
        assertTrue(res.get(0).getName().equals(list.get(0).getName()));
    }

    @Test
    void findById() {
        Optional<Project> project = Optional.ofNullable(
                Project.builder().id(1L).name("test1 name").todos(new HashSet<>()).build()
        );

        when(projectRepository.findById(Mockito.anyLong())).thenReturn(project);

        Project res = projectService.findById(1L);

        verify(projectRepository, times(1)).findById(Mockito.anyLong());

        assertEquals(res.getId(), project.get().getId());
        assertTrue(res.getName().equals(project.get().getName()));
    }

    @Test
    void save() {
        ProjectCommand projectCommand = ProjectCommand.builder().name("test name").build();
        Project project = Project.builder().id(1L).name("test name").todos(new HashSet<>()).build();

        when(projectRepository.save(Mockito.any())).thenReturn(project);

        Project res = projectService.save(projectCommand);

        verify(projectRepository, times(1)).save(Mockito.any());

        assertEquals(res.getId(), project.getId());
        assertTrue(res.getName().equals(project.getName()));
        assertNotNull(res.getTodos());
    }

    @Test
    void deleteById() {
        projectService.deleteById(1L);

        verify(projectRepository, times(1)).deleteById(Mockito.any());
    }
}