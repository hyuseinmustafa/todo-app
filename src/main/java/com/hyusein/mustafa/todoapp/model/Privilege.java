package com.hyusein.mustafa.todoapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    @Builder
    public Privilege(Long id, @NotEmpty String name, Collection<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }
}
