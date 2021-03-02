package com.hyusein.mustafa.todoapp.command;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hyusein.mustafa.todoapp.model.Todo;
import com.hyusein.mustafa.todoapp.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CommentCommand {

    private Long id;

    @NotNull
    private Long todoId;

    @NotEmpty
    private String comment;

    @Builder

    public CommentCommand(Long id, Long todoId, String comment) {
        this.id = id;
        this.todoId = todoId;
        this.comment = comment;
    }
}
