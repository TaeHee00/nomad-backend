package com.kancth.nomad.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotBlank(message = "아이디가 입력되지 않았습니다.")
        String loginId,
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        String password
) {
}
