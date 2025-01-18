package com.kancth.nomad.domain.user.repository;

import com.kancth.nomad.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByLoginId(String loginId);
    Optional<User> findByLoginId(String loginId);
}
