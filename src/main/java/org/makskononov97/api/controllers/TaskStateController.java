package org.makskononov97.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.makskononov97.api.controllers.helpers.ControllerHelper;
import org.makskononov97.api.dto.TaskStateDto;
import org.makskononov97.api.exceptions.BadRequestException;
import org.makskononov97.api.factories.TaskStateDtoFactory;
import org.makskononov97.store.entities.ProjectEntity;
import org.makskononov97.store.entities.TaskStateEntity;
import org.makskononov97.store.repositories.TaskStateRepository;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskStateController {

    TaskStateRepository taskStateRepository;

    TaskStateDtoFactory taskStateDtoFactory;

    ControllerHelper controllerHelper;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";


    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskStates(@PathVariable(name = "project_id") Long projectId) {

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        return project
                .getTaskStates()
                .stream()
                .map(taskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDto createTaskState(
            @PathVariable(name = "project_id") Long projectId,
            @RequestParam(name = "task_state_name") String taskStateName) {

        if (taskStateName.trim().isEmpty()) {
            throw new BadRequestException("Task state name can't be empty.");
        }

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        project
                .getTaskStates()
                .stream()
                .map(TaskStateEntity::getName)
                .filter(anothertaskStateName -> anothertaskStateName.equalsIgnoreCase(taskStateName))
                .findAny()
                .ifPresent(it -> {

                    throw new BadRequestException(String.format("Task state \"s\" already exists.", taskStateName));

                });

        TaskStateEntity taskState = taskStateRepository.saveAndFlush(
                TaskStateEntity.builder()
                        .name(taskStateName)
                        .build()
        );

        taskStateRepository
                .findTaskStateEntityByRightTaskStateIdIsNullAndProjectId(projectId)
                .ifPresent(anotherTaskState -> {

                    taskState.setLeftTaskState(anotherTaskState);

                    anotherTaskState.setRightTaskState(taskState);

                    taskStateRepository.saveAndFlush(anotherTaskState);
                });

        final TaskStateEntity savedTaskState = taskStateRepository.saveAndFlush(taskState);

        return taskStateDtoFactory.makeTaskStateDto(savedTaskState);
    }
}
