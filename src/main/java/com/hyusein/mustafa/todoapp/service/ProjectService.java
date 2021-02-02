package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;

import java.util.List;

public interface ProjectService {
    List<ProjectCommand> findAll();
    ProjectCommand findById(Long id);
    ProjectCommand save(ProjectCommand prj);
    void deleteById(Long id);
}
