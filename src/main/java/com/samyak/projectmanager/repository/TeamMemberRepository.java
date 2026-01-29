package com.samyak.projectmanager.repository;

import com.samyak.projectmanager.entity.TeamMember;
import com.samyak.projectmanager.entity.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("""
        SELECT tm
        FROM TeamMember tm
        JOIN FETCH tm.user
        WHERE tm.team.id = :teamId
          AND tm.isActive = true
    """)
    List<TeamMember> findActiveMembersByTeamId(Long teamId);
}
