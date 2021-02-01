package com.hyusein.mustafa.todoapp.model;

import com.hyusein.mustafa.todoapp.ToDoStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private ToDoStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Todo(Long id, String headline, String description, ToDoStatus status, Project project) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
        this.project = project;
    }
}
