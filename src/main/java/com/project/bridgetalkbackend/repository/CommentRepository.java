package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    public boolean existsByPostPostId(UUID post);

    public List<Comment> findByPostPostId(UUID postId);

    public boolean existsByCommentId(UUID commentId);
}
