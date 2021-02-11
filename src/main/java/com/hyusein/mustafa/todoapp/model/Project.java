package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hyusein.mustafa.todoapp.tool.Tool;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project extends Auditable<String> implements Tool<Project> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Todo> todos;

    @Builder
    public Project(Long id, String name, Set<Todo> todos) {
        this.id = id;
        this.name = name;
        this.todos = todos;
    }

    @Override
    public Project andRemediate(Project src) {
        return remediate(this, src);
    }
}
