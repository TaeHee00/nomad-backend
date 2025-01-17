package com.kancth.nomad.domain.user.service;

import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("SignUp Logic 성공")
    @Test
    void signUp() {
        // Given
        SignUpRequest request = SignUpRequest.builder()
                .loginId("testSignUpId")
                .email("testSignUpEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPw1!")
                .build();
        long beforeActiveUser = userRepository.count();

        // When
        User user = userService.signUp(request);

        // Then
        long afterActiveUser = userRepository.count();
        Assertions.assertThat(beforeActiveUser + 1).isEqualTo(afterActiveUser);

        Assertions.assertThat(request.loginId()).isEqualTo(user.getLoginId());
        Assertions.assertThat(request.email()).isEqualTo(user.getEmail());
        Assertions.assertThat(request.name()).isEqualTo(user.getName());
        Assertions.assertThat(request.nickname()).isEqualTo(user.getNickname());
        Assertions.assertThat(user.isVerified()).isEqualTo(false);
        Assertions.assertThat(user.getProfileImgUrl()).isNull();
    }
}