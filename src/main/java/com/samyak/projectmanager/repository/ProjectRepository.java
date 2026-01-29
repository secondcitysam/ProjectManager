package com.samyak.projectmanager.repository;

import com.samyak.projectmanager.entity.Project;
import com.samyak.projectmanager.entity.ProjectStatus;
import com.samyak.projectmanager.dto.projection.ProjectListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT
            p.id AS id,
            p.name AS name,
            p.status AS status,
            p.updatedAt AS updatedAt,
            p.createdBy AS createdBy
        FROM Project p
        JOIN TeamMember tm ON tm.team = p.team
        WHERE tm.user.id = :userId
          AND tm.isActive = true
          AND p.status = :status
    """)
    Page<ProjectListProjection> findProjectsForUserByStatus(
            @Param("userId") Long userId,
            @Param("status") ProjectStatus status,
            Pageable pageable
    );
}
