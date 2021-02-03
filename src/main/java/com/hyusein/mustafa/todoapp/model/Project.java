package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Todo> todos;

    @Builder
    public Project(Long id, String name, Set<Todo> todos) {
        this.id = id;
        this.name = name;
        this.todos = todos;
    }
}
