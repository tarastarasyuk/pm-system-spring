package com.dboteam.pmsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@ToString(of = {"name", "url", "attachment"})
@NoArgsConstructor
@Entity
@Table(name = "artifact")
public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "attachment_id", referencedColumnName = "id", nullable = false)
    private Attachment attachment;

    public Artifact(String name, String url, Attachment attachment) {
        this.name = name;
        this.url = url;
        this.attachment = attachment;

        this.attachment.getArtifacts().add(this);
    }

    public Artifact(String name, String description, String url, Attachment attachment) {
        this(name, url, attachment);
        this.description = description;
    }
}
