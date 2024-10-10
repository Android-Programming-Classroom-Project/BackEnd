package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.CommentService;
import com.project.bridgetalkbackend.Service.PostService;
import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.dto.PostCommentDTO;
import com.project.bridgetalkbackend.dto.PostUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService,CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    //게시물, 댓글 생성
    @PostMapping("/postMake")
    public ResponseEntity<?> postMake(@RequestBody PostUserDTO postUserDTO){
        Post post = postService.makePost(postUserDTO.getPost());
        commentService.makeComment(postUserDTO.getUser(),post);
        return ResponseEntity.ok().body(post);
    }

    //게시물 조회
    @GetMapping("/{id}")
    public PostCommentDTO getPost(@PathVariable UUID id){
        Post post = postService.postFind(id);
        Comment comment = commentService.getComment(post.getPostId());
        return new PostCommentDTO(post, comment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id){
        postService.postDelete(id);
        return ResponseEntity.ok("삭제가 완료되었습니다");
    }

    //미완성
    @PutMapping("/update/")
    public ResponseEntity<?> updatePost(@RequestBody Post post){

        return ResponseEntity.ok("업데이트 완료되었습니다");
    }

    @PutMapping("/addLiked")
    public ResponseEntity<?> addLiked(@RequestBody PostUserDTO likedDTO){
        postService.addLiked(likedDTO.getUser(),likedDTO.getPost());
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PutMapping("/deleteLiked")
    public ResponseEntity<?> deleteLiked(@RequestBody PostUserDTO likedDTO){
        postService.deleteLiked(likedDTO.getUser(),likedDTO.getPost());
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PostMapping("/addComment")
    public Comment makeComment(@RequestBody Comment comment){
        // 게시물 존재여부
        // 댓글 생성
        if(!postService.checkPost(comment.getPost())){
            throw new IllegalArgumentException("게시물존재 X");
        }
        return commentService.addComment(comment);
    }

    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable UUID id){
        commentService.deleteComment(id);
    }
}
