package org.makskononov97.api.factories;

import org.makskononov97.api.dto.TaskStateDto;
import org.makskononov97.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateFactory {

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity){

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(entity.getOrdinal())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
