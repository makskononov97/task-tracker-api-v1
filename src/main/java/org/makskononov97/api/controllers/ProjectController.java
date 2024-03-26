package org.makskononov97.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.makskononov97.api.dto.ProjectDto;
import org.makskononov97.api.exceptions.BadRequestException;
import org.makskononov97.api.factories.ProjectDtoFactory;
import org.makskononov97.store.repositories.ProjectRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    public static final String CREATE_PROJECT = "/api/projects";

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name){

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });


        //TODO: uncomment and insert entity in method
//        return projectDtoFactory.makeProjectDto();
        return null;
    }
}
