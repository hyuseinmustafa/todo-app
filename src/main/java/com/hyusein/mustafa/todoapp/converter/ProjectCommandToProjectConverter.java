package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectCommandToProjectConverter implements Converter<ProjectCommand, Project> {
    @Override
    public Project convert(ProjectCommand source) {
        if(source == null)return null;

        Project target = new Project();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDeadline(source.getDeadline());
        target.setPriority(source.getPriority());
        return target;
    }
}
