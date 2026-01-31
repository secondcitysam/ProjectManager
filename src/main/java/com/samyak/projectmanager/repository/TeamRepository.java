package com.samyak.projectmanager.repository;

import com.samyak.projectmanager.dto.projection.TeamSummaryProjection;
import com.samyak.projectmanager.dto.response.TeamDetailsDto;
import com.samyak.projectmanager.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("""
        SELECT
            t.id AS id,
            t.name AS name,
            t.description AS description,
            tm.role AS role,
            COUNT(DISTINCT tm2.id) AS membersCount
        FROM Team t
        JOIN TeamMember tm ON tm.team = t
        JOIN TeamMember tm2 ON tm2.team = t AND tm2.isActive = true
        WHERE tm.user.id = :userId
          AND tm.isActive = true
        GROUP BY t.id, tm.role
    """)
    Page<TeamSummaryProjection> findTeamsForUser(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
    SELECT new com.samyak.projectmanager.dto.response.TeamDetailsDto(
        t.id,
        t.name,
        t.description,
        tm.role,
        COUNT(tm2.id)
    )
    FROM Team t
    JOIN TeamMember tm ON tm.team = t
    JOIN TeamMember tm2 ON tm2.team = t AND tm2.isActive = true
    WHERE t.id = :teamId
      AND tm.user.id = :userId
      AND tm.isActive = true
    GROUP BY t.id, tm.role
""")
    TeamDetailsDto findTeamDetails(
            @Param("teamId") Long teamId,
            @Param("userId") Long userId
    );

}
