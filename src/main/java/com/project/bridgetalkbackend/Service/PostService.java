package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Liked;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikedService likedService;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository, LikedService likedService) {
        this.postRepository = postRepository;
        this.likedService = likedService;
    }

    // 게시물 존재여부
    public Boolean checkPost(Post post) {
        return postRepository.existsById(post.getPostId());
    }

    //게시물 수정
    @Transactional
    public Post updatePost(Post post) {
        Post p = postRepository.findById(post.getPostId()).orElseThrow();
        p.setContent(post.getContent());
        p.setTitle(post.getTitle());
        return postRepository.save(p);
    }

    //게시물 생성
    @Transactional
    public Post makePost(Post post, User user) {
        logger.info("PostService Class makePost Method");
        post.setUser(user);
        post.setSchools(user.getSchools());
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post postFind(UUID id) {
        return postRepository.findById(id).orElseThrow();
    }

    //게시물 삭제
    @Transactional
    public void postDelete(UUID id) {
        postRepository.deleteById(id);
    }

    //좋아요 추가
    @Transactional
    public Post addLiked(User user, Post post) {
        if (!likedService.existLiked(user.getUserId(), post.getPostId())) {
            Liked liked = new Liked();
            liked.setPost(post);
            liked.setUser(user);
            likedService.saveLiked(liked);

            Post pt = postRepository.findById(post.getPostId()).orElseThrow(() -> {
                        throw new IllegalArgumentException("좋아요 기능: post or user data가 없음");
                    }
            );
            pt.setLike_count(pt.getLike_count() + 1);
            return postRepository.save(pt);
        }
        throw new IllegalArgumentException("이미 좋아요 누름");
    }

    //좋아요 삭제
    @Transactional
    public void deleteLiked(User user, Post post) {
        // 좋아요가 존재하는지 확인
        if (!likedService.existLiked(user.getUserId(), post.getPostId())) {
            throw new IllegalArgumentException("좋아요 기능: 좋아요가 존재하지 않습니다.");
        }
        // Post 조회
        Post pt = postRepository.findById(post.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("좋아요 기능: 해당 post가 존재하지 않습니다."));

        likedService.deleteLiked(user.getUserId(), post.getPostId());

        // 좋아요 수 감소
        if (pt.getLike_count() > 0) {
            pt.setLike_count(pt.getLike_count() - 1);
            postRepository.save(pt);
        } else {
            throw new IllegalStateException("시스템 오류: 좋아요 수는 이미 0입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<Post> bringEntirePost(UUID schoolId) {
        return postRepository.findBySchoolsSchoolId(schoolId);
    }
}
