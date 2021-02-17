package com.hyusein.mustafa.todoapp.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Builder
    public ProjectCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
