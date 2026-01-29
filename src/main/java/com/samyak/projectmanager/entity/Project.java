package com.samyak.projectmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "projects",
        indexes = {
                @Index(name = "idx_project_team", columnList = "team_id"),
                @Index(name = "idx_project_status", columnList = "status"),
                @Index(name = "idx_project_team_status", columnList = "team_id,status"),
                @Index(name = "idx_project_updated_at", columnList = "updated_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- Ownership ----

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    // ---- Core fields ----

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    // ---- Lifecycle ----

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    private LocalDateTime archivedAt;

    @Column
    private Long archivedBy; // userId (leader)

    // ---- Continuous Audit ----

    @Column(nullable = false)
    private Long createdBy; // userId

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ---- Audit hooks ----

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        status = ProjectStatus.ACTIVE;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
