package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.model.*;
import com.dboteam.pmsystem.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CollaborationService collaborationService;
    private final PositionService positionService;

    public Project createProject(Project project, User user) {
        Project savedProject = projectRepository.save(project);
        Collaboration collaboration = new Collaboration(user, positionService.getPositionByPositionName(PositionName.PROJECT_MANAGER), savedProject);
        collaborationService.saveCollaboration(collaboration);
        return savedProject;
    }

    public Project updateProject(Project sourceProject, Project targetProject) {
        targetProject.setName(sourceProject.getName());
        return projectRepository.save(targetProject);
    }
}
