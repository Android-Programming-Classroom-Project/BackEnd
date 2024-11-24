package com.project.bridgetalkbackend.Controller;

import com.project.bridgetalkbackend.Service.CommentService;
import com.project.bridgetalkbackend.Service.CustomUserDetailsService;
import com.project.bridgetalkbackend.Service.PostService;
import com.project.bridgetalkbackend.domain.Comment;
import com.project.bridgetalkbackend.domain.Post;
import com.project.bridgetalkbackend.domain.User;
import com.project.bridgetalkbackend.dto.PostCommentDTO;
import com.project.bridgetalkbackend.dto.PostUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final CustomUserDetailsService customUserDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService, CommentService commentService, CustomUserDetailsService customUserDetailsService) {
        this.postService = postService;
        this.commentService = commentService;
        this.customUserDetailsService = customUserDetailsService;
    }

    //게시물, 댓글 생성
    @PostMapping("/postMake")
    public ResponseEntity<?> postMake(@RequestBody PostUserDTO postUserDTO){
        logger.info("/postMake");
        User user = customUserDetailsService.findUser(postUserDTO.getUser().getUserId());
        Post postSave = postService.makePost(postUserDTO.getPost(), user);
//        commentService.makeComment(postUserDTO.getUser(),post);
        return ResponseEntity.ok().body(postSave);
    }
    // 전체 게시물
    @GetMapping("/schoolPost/{schoolId}")
    public ResponseEntity<?> SchoolPost(@PathVariable UUID schoolId){
        logger.info("특정학교 전체 게시물 메소드 실행");
        List<Post> posts = postService.bringEntirePost(schoolId);
        return ResponseEntity.ok(posts);
    }

    //게시물 조회
    @GetMapping("/{id}")
    public PostCommentDTO getPost(@PathVariable UUID id){
        Post post = postService.postFind(id);
        List<Comment> comment = commentService.getComment(post.getPostId());
        return new PostCommentDTO(post, comment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id){
        logger.info("/delete/{id}");
        postService.postDelete(id);
        return ResponseEntity.ok("삭제가 완료되었습니다");
    }

    @PutMapping("/update")
    public Post updatePost(@RequestBody Post post){
        logger.info("/update");
        return postService.updatePost(post);
    }

    @PutMapping("/addLiked")
    public ResponseEntity<?> addLiked(@RequestBody PostUserDTO likedDTO){
        logger.info("/addLiked");
        Post post = postService.addLiked(likedDTO.getUser(),likedDTO.getPost());
        return ResponseEntity.ok().body(post);
    }

    @PutMapping("/deleteLiked")
    public ResponseEntity<?> deleteLiked(@RequestBody PostUserDTO likedDTO){
        Post post =  postService.deleteLiked(likedDTO.getUser(),likedDTO.getPost());
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/addComment")
    public Comment makeComment(@RequestBody Comment comment){
        logger.info("/addComment");
        // 게시물 존재여부 및 댓글 생성
        if(!postService.checkPost(comment.getPost())){
            throw new IllegalArgumentException("게시물존재 X");
        }

        if(comment.getUser().getUserId() == null){
            throw new IllegalArgumentException("userIdX :  userId 보내주세요");
        }
        return commentService.addComment(comment);
    }

    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable UUID id, @RequestBody User user){
        if(!customUserDetailsService.checkUser(user.getUserId())){
            throw new IllegalArgumentException("user 정보 X");
        }
        if(!commentService.checkCommentUser(id, user)){
            throw new IllegalArgumentException("해당 user가 댓글 생성한 것이 아님");
        }
        commentService.deleteComment(id);
    }
}
