package com.dboteam.pmsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(of = {"positionName"})
@EqualsAndHashCode(exclude = {"collaborations"})
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_name", unique = true, nullable = false)
    private PositionName positionName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "position")
    private Set<Collaboration> collaborations = new HashSet<>();

    public Position(PositionName positionName) {
        this.positionName = positionName;
    }
}
