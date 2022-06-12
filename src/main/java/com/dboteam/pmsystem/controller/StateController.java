package com.dboteam.pmsystem.controller;

import com.dboteam.pmsystem.exception.ForbiddenException;
import com.dboteam.pmsystem.model.PositionName;
import com.dboteam.pmsystem.model.Project;
import com.dboteam.pmsystem.model.State;
import com.dboteam.pmsystem.service.StateService;
import com.dboteam.pmsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class StateController {

    private static final PositionName POSITION_NAME = PositionName.TEAMLEAD;
    private final UserService userService;
    private final StateService stateService;

    @PostMapping("/{id}/states")
    public ResponseEntity<State> createState(@PathVariable("id") Project project, @RequestBody State state, Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(stateService.createState(state, project), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/states/{stateId}")
    public ResponseEntity<State> updateState(@PathVariable("id") Project project, @RequestBody State sourceState, @PathVariable("stateId") State targetState, Principal principal) {
        checkUserPosition(principal.getName(), project);
        return new ResponseEntity<>(stateService.updateState(sourceState, targetState, project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/states/{stateId}")
    public ResponseEntity<Void> deleteState(@PathVariable("id") Project project, @PathVariable("stateId") State state, Principal principal) {
        checkUserPosition(principal.getName(), project);
        stateService.deleteState(state);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void checkUserPosition(String username, Project project) {
        if (!userService.checkUserPosition(username, POSITION_NAME, project)) {
            throw new ForbiddenException();
        }
    }
}
