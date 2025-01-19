package com.kancth.nomad.domain.project.entity;

import com.kancth.nomad.global.entity.BaseEntity;
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
@SQLDelete(sql = "UPDATE work_scope SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class WorkScope extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // DEVELOPMENT, DESIGN, PLANNING
    private String name;
    // 개발, 디자인, 기획
    private String korName;
    private String imageUrl;
}
