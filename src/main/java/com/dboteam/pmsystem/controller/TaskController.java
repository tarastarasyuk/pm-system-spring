package com.dboteam.pmsystem.controller;

import com.dboteam.pmsystem.exception.ForbiddenException;
import com.dboteam.pmsystem.model.*;
import com.dboteam.pmsystem.service.TaskService;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class TaskController {

    private static final PositionName POSITION_NAME = PositionName.TEAMLEAD;

    private final UserService userService;
    private final TaskService taskService;

    @PostMapping("/{id}/states/{stateId}")
    public ResponseEntity<Task> createTask(@PathVariable("id") Project project, @PathVariable("stateId") State state, @RequestBody Task task, Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(taskService.createTask(task, state), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/states/{stateId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Project project,
                                           @PathVariable("stateId") State state,
                                           @PathVariable("taskId") Task targetTask,
                                           @RequestBody Task sourceTask,
                                           Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(taskService.updateTask(sourceTask, targetTask, state), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/states/{stateId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Project project,
                                           @PathVariable("taskId") Task task,
                                           Principal principal) {
        checkUserPosition(principal.getName(), project);
        taskService.deleteTask(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/states/{stateId}/tasks/{taskId}/assignDeveloper")
    public ResponseEntity<Task> assignDeveloper(@PathVariable("id") Project project,
                                                @PathVariable("taskId") Task task,
                                                @RequestBody User developerUser,
                                                Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(taskService.assignDeveloperForTask(userService.findByUsername(developerUser.getUsername()), task), HttpStatus.OK);
    }

    @PutMapping("/{id}/states/{stateId}/tasks/{taskId}/setDeadline")
    public ResponseEntity<Task> setDeadline(@PathVariable("id") Project project,
                                                @PathVariable("taskId") Task task,
                                                @RequestBody Integer deadlineHours,
                                                Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(taskService.setDeadlineForTask(deadlineHours, task), HttpStatus.OK);
    }


    private void checkUserPosition(String username, Project project) {
        if (!userService.checkUserPosition(username, POSITION_NAME, project)) {
            throw new ForbiddenException();
        }
    }
}
