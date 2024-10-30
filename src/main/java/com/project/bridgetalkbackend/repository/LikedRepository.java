package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikedRepository extends JpaRepository<Liked, UUID> {
    public Liked findByPostPostId(UUID postId);
    public boolean existsByPostPostIdAndUserUserId(UUID postId, UUID userId);

    void deleteByUserUserIdAndPostPostId(UUID userId, UUID postId);
}
