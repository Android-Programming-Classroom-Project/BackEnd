package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    public List<Post> findBySchoolsSchoolId(UUID schoolId);

    public boolean existsById(UUID postId);
    //update 문

}
