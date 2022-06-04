package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"name", "state", "user", "deadline"})
@EqualsAndHashCode(exclude = {"attachments"})
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL")
    )
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "task")
    private Set<Attachment> attachments = new HashSet<>();

    public Task(String name, State state) {
        this.name = name;
        this.state = state;

        this.state.getTasks().add(this);
    }

    public Task(String name, String description, State state) {
        this(name, state);
        this.description = description;
    }

    public Task(String name, State state, LocalDateTime deadline) {
        this(name, state);
        this.deadline = deadline;
    }

    public Task(String name, String description, State state, LocalDateTime deadline) {
        this(name, state);
        this.description = description;
        this.deadline = deadline;
    }
}
