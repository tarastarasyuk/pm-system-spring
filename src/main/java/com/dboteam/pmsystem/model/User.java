package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"username"})
@EqualsAndHashCode(exclude = {"collaborations", "tasks"})
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Collaboration> collaborations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Task> tasks = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @PreRemove
    private void preRemove() {
        tasks.forEach(task -> task.setUser(null));
    }
}
