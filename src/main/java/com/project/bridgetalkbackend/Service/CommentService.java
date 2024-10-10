package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Transactional
    public void makeComment(User user, Post post){
        if(!commentRepository.existsByPostPostId(post.getPostId())){
            Comment comment = null;
            comment.setPost(post);
            comment.setUser(user);
            commentRepository.save(comment);
        }
    }
    @Transactional(readOnly = true)
    public Comment getComment(UUID postId) {
        return commentRepository.findByPostPostId(postId).orElseThrow(
                ()-> new IllegalStateException("게시물 가져오기: 시스템 에러")
        );
    }

    @Transactional
    public Comment addComment(Comment comment){
            return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
