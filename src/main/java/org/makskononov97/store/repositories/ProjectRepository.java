package org.makskononov97.store.repositories;

import org.makskononov97.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;


public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Optional<ProjectEntity> findByName(String name);

    Stream<ProjectEntity> streamAllBy();

    Stream<ProjectEntity> streamAllByNameStartsWithIgnoreCase(String prefixName);

}
