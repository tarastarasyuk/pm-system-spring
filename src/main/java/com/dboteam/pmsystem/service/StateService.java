package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.exception.SuchEntityAlreadyExistsException;
import com.dboteam.pmsystem.model.Project;
import com.dboteam.pmsystem.model.State;
import com.dboteam.pmsystem.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public State createState(State state, Project project) {
        checkIfStateExistsInProject(state, project);
        state.setProject(project);
        return stateRepository.save(state);
    }

    public State updateState(State sourceState, State targetState, Project project) {
        checkIfStateExistsInProject(sourceState, project);
        targetState.setName(sourceState.getName());
        return stateRepository.save(targetState);
    }

    public void deleteState(State state) {
        stateRepository.delete(state);
    }

    private void checkIfStateExistsInProject(State state, Project project) {
        boolean existsByNameInProject = stateRepository.findAll().stream()
                .filter(allState -> allState.getProject().getId().equals(project.getId()))
                .anyMatch(projectState -> projectState.getName().equals(state.getName()));
        if (existsByNameInProject) {
            throw new SuchEntityAlreadyExistsException();
        }
    }
}
