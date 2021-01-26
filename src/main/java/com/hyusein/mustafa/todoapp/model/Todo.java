package com.hyusein.mustafa.todoapp.model;

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
    private String status;

    @Builder
    public Todo(Long id, String headline, String description, String status) {
        this.id = id;
        this.headline = headline;
        this.description = description;
        this.status = status;
    }
}
