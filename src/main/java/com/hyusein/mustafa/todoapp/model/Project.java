package com.hyusein.mustafa.todoapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Todo> todo;

    @Builder
    public Project(Long id, String name, Set<Todo> todo) {
        this.id = id;
        this.name = name;
        this.todo = todo;
    }
}
