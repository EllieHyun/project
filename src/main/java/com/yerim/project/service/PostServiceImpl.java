package com.yerim.project.service;

import com.yerim.project.dto.PostCreateDto;
import com.yerim.project.dto.PostUpdateDto;
import com.yerim.project.entity.Post;
import com.yerim.project.entity.User;
import com.yerim.project.exception.PostNotFoundException;
import com.yerim.project.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    @Override
    public List<Post> findAllByOrderByCreateAtDesc() {
        return postRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void create(PostCreateDto postCreateDto, User user) {
        Post newPost = Post.postRegister()
                .title(postCreateDto.getTitle())
                .text(postCreateDto.getText())
                .user(user)
                .createAt(LocalDateTime.now())
                .build();
        save(newPost);
    }

    @Override
    public Post findById(Long id) throws PostNotFoundException {
        Optional<Post> findById = postRepository.findById(id);
        if(findById.isEmpty()) throw new PostNotFoundException("찾으시는 포스트가 존재하지 않습니다.");
        else return findById.get();
    }

    @Transactional
    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public void update(PostUpdateDto postUpdateDto) throws PostNotFoundException {
        Optional<Post> findById = postRepository.findById(postUpdateDto.getId());
        if(findById.isEmpty()) throw new PostNotFoundException("찾으시는 포스트가 존재하지 않습니다.");
        Post post = findById.get();
        post.setTitle(postUpdateDto.getTitle());
        post.setText(postUpdateDto.getText());
        post.setLastUpdatedAt(LocalDateTime.now());
        save(post);
    }
}
