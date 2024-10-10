package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.User;
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
    public boolean existLiked(UUID userId, UUID postId){
        return likedRepository.existsByPostPostIdAndUserUserId(userId, postId);
    }
    //좋아요 삭제
    @Transactional
    public void deleteLiked(){

    }
}
