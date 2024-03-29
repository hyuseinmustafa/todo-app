package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hyusein.mustafa.todoapp.enums.Priority;
import com.hyusein.mustafa.todoapp.enums.ToDoStatus;
import com.hyusein.mustafa.todoapp.tool.Tool;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo extends Auditable<String> implements Tool{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private ToDoStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "done_by_user_id")
    private User doneBy;

    private Date deadline;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo")
    private Set<Comment> comments;

    @Builder
    public Todo(Long id, String headline, String description, ToDoStatus status, Priority priority,
                Project project, User assignedUser, User doneBy, Date deadline, Set<Comment> comments) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.project = project;
        this.assignedUser = assignedUser;
        this.doneBy = doneBy;
        this.deadline = deadline;
        this.comments = comments;
    }

    public Todo ifIdPresentRemediate(Function<Todo, Todo> source){
        if(this.id == null) return this;
        return Tool.remediate(this, source.apply(this));
    }
}
