package com.project.bridgetalkbackend.Service;

import com.project.bridgetalkbackend.Controller.PostController;
import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Transactional
    public void makeComment(User user, Post post){
        logger.info("CommentService: makeComment Method");
        if(!commentRepository.existsByPostPostId(post.getPostId())){
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            commentRepository.save(comment);
        }
        logger.info("CommentService: makeComment Method Completed");
    }
    @Transactional(readOnly = true)
    public List<Comment> getComment(UUID postId) {
        if(commentRepository.existsByPostPostId(postId)){
            return commentRepository.findByPostPostIdOrderByCreatedAtAsc(postId);
        }
        return null;
    }

    @Transactional
    public Comment addComment(Comment comment){
            return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }

    public boolean checkCommentUser(UUID id, User user) {
        return commentRepository.existsByCommentIdAndUserUserId(id,user.getUserId());
    }
}
