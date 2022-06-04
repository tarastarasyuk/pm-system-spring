package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"name", "project"})
@EqualsAndHashCode(exclude = {"tasks"})
@NoArgsConstructor
@Entity
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "state")
    private Set<Task> tasks = new HashSet<>();

    public State(String name, Project project) {
        this.name = name;
        this.project = project;

        this.project.getStates().add(this);
    }
}
