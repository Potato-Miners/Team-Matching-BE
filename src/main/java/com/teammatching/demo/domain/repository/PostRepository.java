package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteByIdAndUserAccount_UserId(Long postId, String userId);
}
