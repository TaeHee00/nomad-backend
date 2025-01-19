package com.kancth.nomad.domain.user.entity;

import com.kancth.nomad.domain.security.enums.AuthType;
import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
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

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    // 프로필 이미지 url
    private String profileImgUrl;
    // 이메일 인증 여부
    private boolean verified;

    public static User toEntity(SignUpRequest request, String encodePassword) {
        return User.builder()
                .email(request.email())
                .name(request.name())
                .nickname(request.nickname())
                .loginId(request.loginId())
                .password(encodePassword)
                .authType(AuthType.USER)
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
                .authType(this.getAuthType())
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
            AuthType authType,
            boolean verified,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
