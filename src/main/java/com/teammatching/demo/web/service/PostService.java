package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public Page<PostDto.SimpleResponse> getSimplePosts() {
        return null;        //TODO: 비즈니스 로직 구현 필요
    }

    public void createPost(PostDto toDto) {
        //TODO: 비즈니스 로직 구현 필요
    }

    public PostDto getPostById(Long postId) {
        return null;        //TODO: 비즈니스 로직 구현 필요
    }

    public void updatePost(Long postId, PostDto toDto) {
    }

    public void deletePost(Long postId, UserAccountDto build) {
    }
}
