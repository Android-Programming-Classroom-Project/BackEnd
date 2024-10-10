package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.LikedRepository;
import com.project.bridgetalkbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final LikedService likedService;

    @Autowired
    public PostService(PostRepository postRepository, LikedService likedService) {
        this.postRepository = postRepository;
        this.likedService = likedService;
    }
    // 게시물 존재여부
    public Boolean checkPost(Post post){
        return postRepository.existsById(post.getPostId());
    }

    //게시물 생성
    @Transactional
    public Post makePost(Post post){
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post postFind(UUID id){
        return postRepository.findById(id).orElseThrow();
    }

    //게시물 삭제
    @Transactional
    public void postDelete(UUID id){
        postRepository.deleteById(id);
    }

//    @Transactional
//    public void update(Post post){
//        postRepository.
//    }

    //좋아요 추가
    @Transactional
    public void addLiked(User user, Post post){
        if(!likedService.existLiked(user.getUserId(), post.getPostId())){
            Post pt = postRepository.findById(post.getPostId()).orElseThrow(() -> {
                throw new IllegalArgumentException("좋아요 기능: post or user data가 없음");
                    }
            );
            pt.setLike_count(pt.getLike_count()+1);
            postRepository.save(pt);
        }
    }

    //좋아요 삭제
    @Transactional
    public void deleteLiked(User user, Post post){
        if(likedService.existLiked(user.getUserId(), post.getPostId())){
            Post pt = postRepository.findById(post.getPostId()).orElseThrow(() -> {
                        throw new IllegalArgumentException("좋아요 기능: post or user data가 없음");
                    }
            );
            if(pt.getLike_count() > 0){
                pt.setLike_count(pt.getLike_count()-1);
                postRepository.save(pt);
            }
            throw new IllegalStateException("시스템오류(여기는 에러가발생하면 안됌) : 이미 좋아요는 0");
        }
    }


}
