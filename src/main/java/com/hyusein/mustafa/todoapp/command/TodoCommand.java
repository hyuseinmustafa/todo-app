package com.hyusein.mustafa.todoapp.command;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class TodoCommand {

    private Long id;

    @NotBlank
    @Size(min=1, max=50)
    private String headline;

    @Lob
    private String description;

    @NotNull
    private ToDoStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @NotNull
    private ProjectCommand project;

    @Builder

    public TodoCommand(Long id, String headline, String description, ToDoStatus status, Date deadline, ProjectCommand project) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.project = project;
    }
}
