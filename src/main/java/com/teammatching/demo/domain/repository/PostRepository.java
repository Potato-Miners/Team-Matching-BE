package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUserAccount_UserId(String userId, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long postId, String userId);
}
