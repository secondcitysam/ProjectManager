package com.samyak.projectmanager.service.team;

import com.samyak.projectmanager.config.security.SecurityUtils;
import com.samyak.projectmanager.dto.projection.TeamSummaryProjection;
import com.samyak.projectmanager.dto.request.CreateTeamRequest;
import com.samyak.projectmanager.dto.response.TeamDetailsDto;
import com.samyak.projectmanager.entity.Team;
import com.samyak.projectmanager.entity.TeamMember;
import com.samyak.projectmanager.entity.TeamRole;
import com.samyak.projectmanager.entity.User;
import com.samyak.projectmanager.repository.TeamMemberRepository;
import com.samyak.projectmanager.repository.TeamRepository;
import com.samyak.projectmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    @Override
    public void createTeam(CreateTeamRequest request) {

        // ---- AUDIT POINT #1: current user ----
        User currentUser = SecurityUtils.getCurrentUser();


        // ---- Create team ----
        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(currentUser.getId())
                .build();

        teamRepository.save(team);

        // ---- Create leader membership ----
        TeamMember leader = TeamMember.builder()
                .team(team)
                .user(currentUser)
                .role(TeamRole.LEADER)
                .isActive(true)
                .build();

        teamMemberRepository.save(leader);
    }

    @Override
    public void addMember(Long teamId, String userIdentifier) {

        User currentUser = SecurityUtils.getCurrentUser();


        // ---- AUDIT POINT #2: leader check ----
        boolean isLeader = teamMemberRepository
                .existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
                        teamId,
                        currentUser.getId(),
                        TeamRole.LEADER
                );

        if (!isLeader) {
            throw new RuntimeException("Only team leader can add members");
        }

        User userToAdd = userRepository.findByUsername(userIdentifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        teamMemberRepository.findByTeamIdAndUserIdAndIsActiveTrue(teamId, userToAdd.getId())
                .ifPresent(tm -> {
                    throw new RuntimeException("User already a team member");
                });

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        TeamMember member = TeamMember.builder()
                .team(team)
                .user(userToAdd)
                .role(TeamRole.MEMBER)
                .isActive(true)
                .build();

        teamMemberRepository.save(member);
    }

    @Override
    public void removeMember(Long teamId, Long userId) {

        User currentUser = SecurityUtils.getCurrentUser();

        boolean isLeader = teamMemberRepository
                .existsByTeamIdAndUserIdAndRoleAndIsActiveTrue(
                        teamId,
                        currentUser.getId(),
                        TeamRole.LEADER
                );

        if (!isLeader) {
            throw new RuntimeException("Only team leader can remove members");
        }

        if (currentUser.getId().equals(userId)) {
            throw new RuntimeException("Leader cannot remove self");
        }

        TeamMember member = teamMemberRepository
                .findByTeamIdAndUserIdAndIsActiveTrue(teamId, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setIsActive(false);
        member.setRemovedAt(java.time.LocalDateTime.now());

        teamMemberRepository.save(member);
    }


    @Override
    public Page<TeamSummaryProjection> getTeamsForCurrentUser(Pageable pageable) {

        User currentUser = SecurityUtils.getCurrentUser();

        return teamRepository.findTeamsForUser(
                currentUser.getId(),
                pageable
        );
    }

    @Override
    public TeamDetailsDto getTeamDetails(Long teamId) {

        User currentUser = SecurityUtils.getCurrentUser();

        TeamDetailsDto details = teamRepository.findTeamDetails(
                teamId,
                currentUser.getId()
        );

        if (details == null) {
            throw new RuntimeException("Team not found or access denied");
        }

        return details;
    }

}
