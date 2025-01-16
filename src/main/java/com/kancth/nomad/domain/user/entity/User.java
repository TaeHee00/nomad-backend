package com.kancth.nomad.domain.user.entity;

import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String nickname;
    private String loginId;
    private String password;

    // 프로필 이미지 url
    private String profileImgUrl;
    // 이메일 인증 여부
    private boolean verified;

    public static User toEntity(SignUpRequest request) {
        return User.builder()
                .email(request.email())
                .name(request.name())
                .nickname(request.nickname())
                .loginId(request.loginId())
                .password(request.password())
                .verified(false)
                .build();
    }

    public User.Response response() {
        return Response.builder()
                .id(this.getId())
                .email(this.getEmail())
                .name(this.getName())
                .nickname(this.getNickname())
                .loginId(this.getLoginId())
                .password(this.getPassword())
                .verified(this.isVerified())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    @Builder
    public record Response (
            Long id,
            String email,
            String name,
            String nickname,
            String loginId,
            String password,
            boolean verified,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
