package com.samyak.projectmanager.dto.projection;

import com.samyak.projectmanager.entity.TeamRole;

public interface TeamSummaryProjection {

    Long getId();
    String getName();
    String getDescription();
    TeamRole getRole();
    Long getMembersCount();
}
