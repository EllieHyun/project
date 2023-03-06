package com.yerim.project.service;

import com.yerim.project.dto.PostCreateDto;
import com.yerim.project.dto.PostUpdateDto;
import com.yerim.project.entity.Post;
import com.yerim.project.entity.User;
import com.yerim.project.exception.PostNotFoundException;

import java.util.List;

public interface PostService {

    List<Post> findAllByOrderByCreateAtDesc();
    Post save(Post post);
    void create(PostCreateDto postCreateDto, User user);
    Post findById(Long id) throws PostNotFoundException;
    void delete(Post post);
    void update(PostUpdateDto postUpdateDto) throws PostNotFoundException;
}
