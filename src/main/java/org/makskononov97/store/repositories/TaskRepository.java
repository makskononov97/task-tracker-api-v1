package org.makskononov97.store.repositories;

import org.makskononov97.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
