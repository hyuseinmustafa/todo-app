package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.converter.ProjectCommandToProjectConverter;
import com.hyusein.mustafa.todoapp.converter.ProjectToProjectCommandConverter;
import com.hyusein.mustafa.todoapp.model.Project;
import com.hyusein.mustafa.todoapp.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Transactional
    @Override
    public Project save(ProjectCommand prj) {
        return repository.save(new ProjectCommandToProjectConverter().convert(prj));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProjectCommand> findAllAsCommand() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(new ProjectToProjectCommandConverter()::convert).collect(Collectors.toList());
    }
}
