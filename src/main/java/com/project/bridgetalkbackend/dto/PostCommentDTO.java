package com.project.bridgetalkbackend.dto;

import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;

import java.util.List;

public class PostCommentDTO {
    private Post post;
    private List<Comment> commentList;

    public PostCommentDTO(Post post, List<Comment> commentList) {
        this.post = post;
        this.commentList = commentList;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
