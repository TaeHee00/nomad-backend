package com.kancth.nomad.domain.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE project_field SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class ProjectField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_field")
    private SubField mainField;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_field")
    private SubField secondField;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_field")
    private SubField thirdField;
}
