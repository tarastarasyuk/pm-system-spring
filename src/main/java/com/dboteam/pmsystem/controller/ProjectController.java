package com.dboteam.pmsystem.controller;

import com.dboteam.pmsystem.exception.ForbiddenException;
import com.dboteam.pmsystem.model.*;
import com.dboteam.pmsystem.service.CollaborationService;
import com.dboteam.pmsystem.service.PositionService;
import com.dboteam.pmsystem.service.ProjectService;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private static final PositionName POSITION_NAME = PositionName.PROJECT_MANAGER;
    private final UserService userService;
    private final ProjectService projectService;
    private final CollaborationService collaborationService;
    private final PositionService positionService;

    @GetMapping
    public Set<Project> getProjects(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Set<Project> projectSet = new HashSet<>();
        user.getCollaborations().forEach(collaboration -> projectSet.add(collaboration.getProject()));
        return projectSet;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(projectService.createProject(project, user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable("id") Project project) {
        return project;
    }

    @PutMapping("/{id}")
    public Project updateProject(@RequestBody Project sourceProject, @PathVariable("id") Project targetProject, Principal principal) {
        checkUserPosition(principal.getName(), targetProject);
        return projectService.updateProject(sourceProject, targetProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Project project, Principal principal) {
        checkUserPosition(principal.getName(), project);
        projectService.deleteProject(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/{id}/users")
    public ResponseEntity<Set<Collaboration>> setDevelopersForProject(@PathVariable("id") Project project,
                                                                      @RequestBody Map<String, String> usersBody,
                                                                      Principal principal) {
        if (!userService.checkUserPosition(principal.getName(), PositionName.PROJECT_MANAGER, project)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Set<Collaboration> savedCollaborators = new HashSet<>();

        usersBody.forEach((username, positionName) -> {
            if (userService.userExists(username)) {
                User targetUser = userService.findByUsername(username);
                Collaboration collaboration = new Collaboration(targetUser, positionService.getPositionByPositionName(PositionName.valueOf(positionName)), project);
                collaborationService.saveCollaboration(collaboration);
                savedCollaborators.add(collaboration);
            }
        });
        return new ResponseEntity<>(savedCollaborators, HttpStatus.OK);
    }

    private void checkUserPosition(String username, Project project) {
        if (!userService.checkUserPosition(username, POSITION_NAME, project)) {
            throw new ForbiddenException();
        }
    }
}
