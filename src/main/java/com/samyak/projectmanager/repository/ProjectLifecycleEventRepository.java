package com.samyak.projectmanager.repository;

import com.samyak.projectmanager.entity.ProjectLifecycleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectLifecycleEventRepository
        extends JpaRepository<ProjectLifecycleEvent, Long> {

    List<ProjectLifecycleEvent> findByProjectIdOrderByEventTimeAsc(Long projectId);
}
