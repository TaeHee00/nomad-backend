package com.kancth.nomad.domain.project.entity;

import com.kancth.nomad.domain.project.enums.*;
import com.kancth.nomad.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE project SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String description;
    private Long budget;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_location_id")
    private MajorLocation majorLocation;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_location_id")
    private SubLocation subLocation;

    private LocalDate startDate;
    private LocalDate applyDeadline;
    private Integer progressPeriod;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    @Enumerated(EnumType.STRING)
    private ProgressType progressType;
    @Enumerated(EnumType.STRING)
    private PlanningStatusType planningStatusType;
    @Enumerated(EnumType.STRING)
    private MeetingType preMeetingType;
    @Enumerated(EnumType.STRING)
    private MeetingType progressMeetingType;
    @Enumerated(EnumType.STRING)
    private MeetingCycleType meetingCycleType;
}
