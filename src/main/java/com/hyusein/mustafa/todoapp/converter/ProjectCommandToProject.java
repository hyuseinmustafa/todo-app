package com.hyusein.mustafa.todoapp.converter;

import com.hyusein.mustafa.todoapp.command.ProjectCommand;
import com.hyusein.mustafa.todoapp.model.Project;
import org.springframework.core.convert.converter.Converter;

public class ProjectCommandToProject implements Converter<ProjectCommand, Project> {
    @Override
    public Project convert(ProjectCommand source) {
        Project target = new Project();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
