package com.hyusein.mustafa.todoapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class ProjectCommand {
    private Long id;
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
}