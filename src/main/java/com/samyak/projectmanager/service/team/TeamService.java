package com.samyak.projectmanager.service.team;

import com.samyak.projectmanager.dto.request.CreateTeamRequest;

public interface TeamService {

    void createTeam(CreateTeamRequest request);

    void addMember(Long teamId, String userIdentifier);

    void removeMember(Long teamId, Long userId);
}
