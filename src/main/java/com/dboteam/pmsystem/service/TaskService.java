package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.exception.SuchEntityAlreadyExistsException;
import com.dboteam.pmsystem.exception.UserNotProjectParticipantException;
import com.dboteam.pmsystem.model.State;
import com.dboteam.pmsystem.model.Task;
import com.dboteam.pmsystem.model.User;
import com.dboteam.pmsystem.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CollaborationService collaborationService;

    public Task createTask(Task task, State state) {
        checkIfTaskExistsInProject(state, task);
        task.setState(state);
        return taskRepository.save(task);
    }

    public Task updateTask(Task sourceTask, Task targetTask, State state) {
        checkIfTaskExistsInProject(state, sourceTask);
        targetTask.setName(sourceTask.getName());
        targetTask.setDescription(sourceTask.getDescription());
        targetTask.setDeadline(sourceTask.getDeadline());
        return taskRepository.save(targetTask);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    private void checkIfTaskExistsInProject(State state, Task task) {
        boolean existsByNameInProject = taskRepository.findAll().stream()
                .filter(allTask -> allTask.getState().getProject().getId().equals(state.getProject().getId()))
                .anyMatch(stateTask -> stateTask.getName().equals(task.getName()));
        if (existsByNameInProject) {
            throw new SuchEntityAlreadyExistsException();
        }
    }

    public Task assignDeveloperForTask(User user, Task task) {
        if (collaborationService.getCollaborationsByProject(task.getState().getProject()).stream()
                .noneMatch(collaboration -> collaboration.getUser().getUsername().equals(user.getUsername()))) {
            throw new UserNotProjectParticipantException();
        }
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task setDeadlineForTask(Integer deadlineHours, Task task) {
        task.setDeadline(deadlineHours);
        return taskRepository.save(task);
    }
}
