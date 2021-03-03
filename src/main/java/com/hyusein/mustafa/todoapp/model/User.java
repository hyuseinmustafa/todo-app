package com.hyusein.mustafa.todoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    private String password;

    private String firstName;
    private String lastName;
    private boolean enabled;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Comment> comments;

    @Builder
    public User(Long id, String username, String password, String firstName, String lastName,
                boolean enabled, String email, Collection<Role> roles, Set<Comment> comments) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.email = email;
        this.roles = roles;
        this.comments = comments;
    }
}
