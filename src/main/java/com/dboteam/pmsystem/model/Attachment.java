package com.dboteam.pmsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(of = {"task"})
@EqualsAndHashCode(exclude = {"artifacts"})
@NoArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "attachment")
    private Set<Artifact> artifacts = new HashSet<>();

    public Attachment(Task task) {
        this.task = task;
        this.task.getAttachments().add(this);
    }

    public Attachment(String comment, Task task) {
        this(task);
        this.comment = comment;
    }

    @PreRemove
    public void remove() {
        task.getAttachments().remove(this);
    }
}
