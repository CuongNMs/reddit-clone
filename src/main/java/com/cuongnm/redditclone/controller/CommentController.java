package com.cuongnm.redditclone.controller;

import com.cuongnm.redditclone.dto.CommentDto;
import com.cuongnm.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentForPost(@RequestParam("postId") Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@RequestParam("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByUser(username));
    }

}
