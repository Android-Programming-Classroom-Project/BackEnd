package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;

public class PostUserDTO {
    private Post post;
    private User user;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
