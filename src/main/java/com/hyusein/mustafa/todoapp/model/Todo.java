package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hyusein.mustafa.todoapp.ToDoStatus;
import com.hyusein.mustafa.todoapp.tool.Tool;
import lombok.*;

import javax.persistence.*;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private ToDoStatus status;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @Builder
    public Todo(Long id, String headline, String description, ToDoStatus status, Project project, User assignedUser) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
        this.project = project;
        this.assignedUser = assignedUser;
    }

    public Todo ifIdPresentRemediate(Function<? super Todo, ? extends Todo> source){
        if(this.id != null) Tool.remediate(this, source.apply(this));
        return this;
    }
}
