package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;

public class PostCommentDTO {
    private Post post;
    private Comment comment;

    public PostCommentDTO(Post post, Comment comment) {
        this.post = post;
        this.comment = comment;
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
