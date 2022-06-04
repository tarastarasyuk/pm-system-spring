package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.model.Collaboration;
import com.dboteam.pmsystem.model.Position;
import com.dboteam.pmsystem.model.Project;
import com.dboteam.pmsystem.model.User;
import com.dboteam.pmsystem.repository.CollaborationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CollaborationService {
    private final CollaborationRepository collaborationRepository;

    public Collaboration saveCollaboration(Collaboration collaboration) {
        return collaborationRepository.save(collaboration);
    }

    public boolean userHasPositionInProject(User user, Position position, Project project) {
        return collaborationRepository.existsCollaborationByUserAndPositionAndProject(user, position, project);
    }
}
