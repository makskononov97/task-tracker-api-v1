package org.makskononov97.store.repositories;

import org.makskononov97.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {

    Optional<TaskStateEntity> findTaskStateEntityByProjectIdAndNameContainsIgnoreCase
            (Long projectId, String taskStateName);

}
