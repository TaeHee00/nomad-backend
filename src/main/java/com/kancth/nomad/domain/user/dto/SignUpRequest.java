package com.kancth.nomad.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "아이디가 입력되지 않았습니다.")
        String loginId,
        @NotBlank(message = "이메일이 입력되지 않았습니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotBlank(message = "이름이 입력되지 않았습니다.")
        String name,
        @NotBlank(message = "닉네임이 입력되지 않았습니다.")
        String nickname,
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        String password
) {
}
