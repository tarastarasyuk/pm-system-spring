package com.dboteam.pmsystem.controller;

import com.dboteam.pmsystem.exception.ForbiddenException;
import com.dboteam.pmsystem.model.*;
import com.dboteam.pmsystem.service.ArtifactService;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ArtifactController {

    private static final PositionName POSITION_NAME_1 = PositionName.TEAMLEAD;
    private static final PositionName POSITION_NAME_2 = PositionName.DEVELOPER;
    private final UserService userService;
    private final ArtifactService artifactService;

    @PutMapping("/{id}/states/{stateId}/tasks/{taskId}/attachments/{attachmentId}/artifacts/{artifactId}")
    public ResponseEntity<Artifact> updateArtifact(@PathVariable("id") Project project,
                                                   @PathVariable("artifactId") Artifact targetArtifact,
                                                   @RequestBody Artifact sourceArtifact,
                                                   Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(artifactService.updateArtifact(sourceArtifact, targetArtifact), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/states/{stateId}/tasks/{tarsId}/attachments/{attachmentId}/artifacts/{artifactId}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable("id") Project project,
                                               @PathVariable("artifactId") Artifact artifact,
                                               Principal principal) {
        checkUserPosition(principal.getName(), project);
        artifactService.deleteArtifact(artifact);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void checkUserPosition(String username, Project project) {
        if (!userService.checkUserPosition(username, POSITION_NAME_1, project) &&
                !userService.checkUserPosition(username, POSITION_NAME_2, project)) {
            throw new ForbiddenException();
        }
    }
}
