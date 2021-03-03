package com.hyusein.mustafa.todoapp.command;

import com.hyusein.mustafa.todoapp.enums.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class ProjectCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    private Priority priority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Builder
    public ProjectCommand(Long id, String name, Priority priority, Date deadline) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
    }
}
