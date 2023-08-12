package com.myblog.blogapp.controller;


import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComments(@PathVariable("postId") long postId,@RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable("postId")long postId){
        List<CommentDto>dto=commentService.getCommentByPostId(postId);
        return dto;
    }

}
