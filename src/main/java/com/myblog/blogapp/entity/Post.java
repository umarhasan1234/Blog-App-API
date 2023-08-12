package com.myblog.blogapp.entity;



import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}

)
public class Post {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name="content",nullable = false)
    private String content;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="description",nullable = false)
    private String description;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Comment> comments=new HashSet<>();


}
