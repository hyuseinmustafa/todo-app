package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import org.springframework.core.convert.converter.Converter;

public class ProjectToProjectCommand implements Converter<Project, ProjectCommand> {
    @Override
    public ProjectCommand convert(Project source) {
        ProjectCommand target = new ProjectCommand();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
