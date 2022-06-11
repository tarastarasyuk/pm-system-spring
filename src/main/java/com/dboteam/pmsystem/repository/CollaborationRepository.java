package com.dboteam.pmsystem.repository;

import com.dboteam.pmsystem.model.Collaboration;
import com.dboteam.pmsystem.model.Position;
import com.dboteam.pmsystem.model.Project;
import com.dboteam.pmsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
    boolean existsCollaborationByUserAndPositionAndProject(User user, Position position, Project project);
    List<Collaboration> findCollaborationByProject(Project project);
}
