package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entity.Comment;
import com.myblog.blogapp.entity.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.reprository.CommentRepository;
import com.myblog.blogapp.reprository.PostRepository;
import com.myblog.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment save = commentRepository.save(comment);
        return mapToDto(new Comment());


    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(x->mapToDto(x)).collect(Collectors.toList());

    }

    Comment mapToComment(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment=new Comment();
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return comment;


    }
    CommentDto mapToDto(Comment comment){
        CommentDto commentDto= mapper.map(comment, CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}