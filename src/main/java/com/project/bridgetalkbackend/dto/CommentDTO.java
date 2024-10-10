package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;

public class CommentDTO {
    private User user;
    private Post post;
    private Comment comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
