package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional(readOnly = true)
    public User findByEmail(String email);
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email);
}
