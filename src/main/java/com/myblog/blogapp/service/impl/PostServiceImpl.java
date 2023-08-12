package com.myblog.blogapp.service.impl;

import com.myblog.blogapp.entity.Post;
import com.myblog.blogapp.exception.ResourceNotFoundException;
import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.reprository.PostRepository;
import com.myblog.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    private ModelMapper mapper;

    public PostServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> contents = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post=mapToEntity(postDto);
        Post postEntity = postRepo.save(post);


        //for storing the data into dto layer again for conformation

        PostDto dto=mapToDto(postEntity);

        return dto;
    }
    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }
    public PostDto mapToDto(Post postEntity){

        PostDto dto = mapper.map(postEntity, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(postEntity.getId());
//        dto.setTitle(postEntity.getTitle());
//        dto.setDescription(postEntity.getDescription());
//        dto.setContent(postEntity.getContent());
        return dto;
    }

    public PostDto getPostBYId(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("Post", "id", id));


        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post post1 = postRepo.save(post);
        return mapToDto(post1);

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }
}
