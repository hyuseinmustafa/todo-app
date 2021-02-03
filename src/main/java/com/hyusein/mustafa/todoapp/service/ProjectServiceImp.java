package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.converter.ProjectCommandToProjectConverter;
import com.hyusein.mustafa.todoapp.converter.ProjectToProjectCommandConverter;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProjectServiceImp implements ProjectService{
    private final ProjectRepository repository;

    public ProjectServiceImp(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> findAll() {
        List<Project> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Project findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Project save(ProjectCommand prj) {
        return repository.save(new ProjectCommandToProjectConverter().convert(prj));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProjectCommand> findAllAsCommand() {
        List<ProjectCommand> list = new ArrayList<>();
        repository.findAll().forEach(val -> list.add(new ProjectToProjectCommandConverter().convert(val)));
        return list;
    }
}
