package com.kancth.nomad.domain.user.service;

import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.exception.EmailAlreadyExistsException;
import com.kancth.nomad.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원가입 메서드
    public User signUp(SignUpRequest request) {
        checkLoginIdDuplicate(request.loginId());
        checkEmailDuplicate(request.email());

        // TODO: Password Encoding
        // TODO: Test Code 작성
        return userRepository.save(User.toEntity(request));
    }

    private void checkEmailDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }
    private void checkLoginIdDuplicate(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new EmailAlreadyExistsException();
        }
    }
}