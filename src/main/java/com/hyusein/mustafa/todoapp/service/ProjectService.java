package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    List<ProjectCommand> findAllAsCommand();
    Project findById(Long id);
    Project save(ProjectCommand prj);
    void deleteById(Long id);
}
