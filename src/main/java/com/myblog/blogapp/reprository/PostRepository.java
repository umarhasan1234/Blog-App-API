package com.myblog.blogapp.reprository;

import com.myblog.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post,Long> {
}
