package org.makskononov97.store.repositories;

import org.makskononov97.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.DoubleStream;


public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {

//    Optional<TaskStateEntity> findTaskStateEntityByRightTaskStateIdIsNullAndProjectId
//            (Long projectId);

    Optional<TaskStateEntity> findTaskStateEntityByProjectIdAndNameContainsIgnoreCase
            (Long taskStateid, String taskStateName);
}
