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
@SQLDelete(sql = "UPDATE category SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // WEB, ANDROID, IOS, PC_PROGRAM, EMBEDDED, OTHER
    private String name;
    // 웹, 안드로이드, iOS, PC 프로그램, 임베디드, 기타
    private String korName;
    private String type;
}
