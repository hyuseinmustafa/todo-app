package com.hyusein.mustafa.todoapp.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class ProjectCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @Builder
    public ProjectCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
