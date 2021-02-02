package com.hyusein.mustafa.todoapp.command;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.model.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class TodoCommand {
    private Long id;
    @NotNull
    @Size(min=1, max=50)
    private String headline;
    @Lob
    private String description;
    @NotNull
    private ToDoStatus status;
    @NotNull
    private ProjectCommand project;

    @Builder
    public TodoCommand(Long id, String headline, String description, ToDoStatus status, ProjectCommand project) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
        this.project = project;
    }
}
