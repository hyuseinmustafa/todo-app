package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    @Builder
    public Comment(Long id, Todo todo, User user, String comment) {
        this.id = id;
        this.todo = todo;
        this.user = user;
        this.comment = comment;
    }
}
