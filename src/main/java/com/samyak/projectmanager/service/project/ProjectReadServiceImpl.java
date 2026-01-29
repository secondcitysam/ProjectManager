package com.samyak.projectmanager.service.project;

import com.samyak.projectmanager.config.security.SecurityUtils;
import com.samyak.projectmanager.dto.projection.ProjectListProjection;
import com.samyak.projectmanager.entity.ProjectStatus;
import com.samyak.projectmanager.entity.User;
import com.samyak.projectmanager.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectReadServiceImpl implements ProjectReadService {

    private final ProjectRepository projectRepository;

    @Override
    public Page<ProjectListProjection> getProjectsForCurrentUser(
            ProjectStatus status,
            Pageable pageable
    ) {

        User currentUser = SecurityUtils.getCurrentUser();


        return projectRepository.findProjectsForUserByStatus(
                currentUser.getId(),
                status,
                pageable
        );
    }
}
