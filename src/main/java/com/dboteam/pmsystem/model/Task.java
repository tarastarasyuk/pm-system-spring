package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @PreRemove
    public void removeAssigners() {
        User user = new User();
        user.setId(12l);
        user.setUsername("ff");
        user.setPassword("ff");
        setUser(user);
    }

}
