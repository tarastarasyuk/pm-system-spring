package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@ToString(of = {"user", "role", "project"})
@NoArgsConstructor
@Entity
@Table(name = "collaboration")
public class Collaboration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    public Collaboration(User user, Role role, Project project) {
        this.user = user;
        this.role = role;
        this.project = project;

        this.user.getCollaborations().add(this);
        this.role.getCollaborations().add(this);
        this.project.getCollaborations().add(this);
    }
}
