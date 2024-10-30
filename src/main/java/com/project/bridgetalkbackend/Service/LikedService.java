package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Liked;
import com.project.bridgetalkbackend.repository.LikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LikedService {
    private final LikedRepository likedRepository;

    @Autowired
    public LikedService(LikedRepository likedRepository) {
        this.likedRepository = likedRepository;
    }

    //좋아요 추가
    @Transactional(readOnly = true)
    public boolean existLiked(UUID userId, UUID postId) {
        return likedRepository.existsByPostPostIdAndUserUserId(postId, userId);
    }

    //좋아요 추가
    @Transactional
    public void saveLiked(Liked liked) {
        likedRepository.save(liked);
    }

    //좋아요 삭제
    @Transactional
    public void deleteLiked(UUID userId, UUID postId) {
        if (existLiked(userId, postId)) {
            likedRepository.deleteByUserUserIdAndPostPostId(userId, postId);
        }
    }
}
