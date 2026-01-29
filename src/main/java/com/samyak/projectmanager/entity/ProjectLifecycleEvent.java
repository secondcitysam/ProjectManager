package com.samyak.projectmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "project_lifecycle_events",
        indexes = {
                @Index(name = "idx_event_project", columnList = "project_id"),
                @Index(name = "idx_event_type", columnList = "event_type"),
                @Index(name = "idx_event_time", columnList = "event_time")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectLifecycleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---- Relationship ----

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    // ---- Event Info ----

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectLifecycleEventType eventType;

    @Column(nullable = false)
    private Long performedBy; // userId

    @Column(nullable = false)
    private LocalDateTime eventTime;

    @Column(length = 500)
    private String note; // optional reason

    // ---- Audit hook ----

    @PrePersist
    void onCreate() {
        eventTime = LocalDateTime.now();
    }
}
