package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Liked;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;

import java.util.List;

public class InitDTO {
    private User user;
    private List<Post> post;

    public InitDTO(User user, List<Post> post) {
        this.user = user;
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

}
