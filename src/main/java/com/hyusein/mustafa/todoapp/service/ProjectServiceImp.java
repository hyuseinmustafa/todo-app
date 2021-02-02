package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.converter.ProjectCommandToProjectConverter;
import com.hyusein.mustafa.todoapp.converter.ProjectToProjectCommandConverter;
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
    public List<ProjectCommand> findAll() {
        List<ProjectCommand> list = new ArrayList<>();
        repository.findAll().forEach(value -> list.add(new ProjectToProjectCommandConverter().convert(value)));
        return list;
    }

    @Override
    public ProjectCommand findById(Long id) {
        return new ProjectToProjectCommandConverter().convert(repository.findById(id).orElse(null));
    }

    @Override
    public ProjectCommand save(ProjectCommand prj) {
        return new ProjectToProjectCommandConverter().convert(
                repository.save(new ProjectCommandToProjectConverter().convert(prj)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
