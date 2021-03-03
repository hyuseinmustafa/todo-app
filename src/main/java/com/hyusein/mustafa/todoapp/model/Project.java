package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hyusein.mustafa.todoapp.enums.Priority;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date deadline;
    
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @JsonBackReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Todo> todos;

    @Builder
    public Project(Long id, String name, Date deadline, Priority priority, Set<Todo> todos) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.todos = todos;
    }
}
