package com.samyak.projectmanager.repository;

import com.samyak.projectmanager.dto.response.TeamMemberDto;
import com.samyak.projectmanager.entity.TeamMember;
import com.samyak.projectmanager.entity.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findByTeamIdAndUserIdAndIsActiveTrue(
            Long teamId,
            Long userId
    );

    boolean existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
            Long teamId,
            Long userId,
            TeamRole role
    );


    boolean existsByTeamIdAndUserIdAndIsActiveTrue(
            Long teamId,
            Long userId
    );

    @Query("""
        SELECT tm
        FROM TeamMember tm
        JOIN FETCH tm.user
        WHERE tm.team.id = :teamId
          AND tm.isActive = true
    """)
    List<TeamMember> findActiveMembersByTeamId(Long teamId);

    long countByUserIdAndIsActiveTrue(Long userId);

    @Query("""
    SELECT new com.samyak.projectmanager.dto.response.TeamMemberDto(
        u.id,
        u.username,
        tm.role
    )
    FROM TeamMember tm
    JOIN tm.user u
    WHERE tm.team.id = :teamId
      AND tm.isActive = true
""")
    List<TeamMemberDto> findActiveMembers(
            @Param("teamId") Long teamId
    );
}
