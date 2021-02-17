package com.hyusein.mustafa.todoapp.command;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AssignCommand {

    @NotNull
    private Long todoId;
    @NotNull
    private String assignedUser;
    private Set<String> userList = new HashSet<>();

    @Builder
    public AssignCommand(Long todoId, String assignedUser, Set<String> userList) {
        this.todoId = todoId;
        this.assignedUser = assignedUser;
        this.userList = userList;
    }
}
