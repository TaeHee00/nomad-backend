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
@SQLDelete(sql = "UPDATE techStack SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class TechStack extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
}
