package com.dboteam.pmsystem.controller;

import com.dboteam.pmsystem.exception.ForbiddenException;
import com.dboteam.pmsystem.model.*;
import com.dboteam.pmsystem.service.AttachmentService;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class AttachmentController {

    private static final PositionName POSITION_NAME_1 = PositionName.TEAMLEAD;
    private static final PositionName POSITION_NAME_2 = PositionName.DEVELOPER;
    private final UserService userService;
    private final AttachmentService attachmentService;

    @PostMapping("/{id}/states/{stateId}/tasks/{taskId}")
    public ResponseEntity<Attachment> createAttachment(@PathVariable("id") Project project,
                                                 @PathVariable("taskId") Task task,
                                                 @RequestBody Attachment attachment,
                                                 Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(attachmentService.createAttachment(attachment, task), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/states/{stateId}/tasks/{taskId}/attachments/{attachmentId}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable("id") Project project,
                                                 @PathVariable("taskId") Task task,
                                                 @PathVariable("attachmentId") Attachment targetAttachment,
                                                 @RequestBody Attachment sourceAttachment,
                                                 Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(attachmentService.updateAttachment(sourceAttachment, targetAttachment, task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/states/{stateId}/tasks/{tarsId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable("id") Project project,
                                                 @PathVariable("attachmentId") Attachment attachment,
                                                 Principal principal) {
        checkUserPosition(principal.getName(), project);
        attachmentService.deleteAttachment(attachment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkUserPosition(String username, Project project) {
        if (!userService.checkUserPosition(username, POSITION_NAME_1, project) &&
                !userService.checkUserPosition(username, POSITION_NAME_2, project)) {
            throw new ForbiddenException();
        }
    }
}
