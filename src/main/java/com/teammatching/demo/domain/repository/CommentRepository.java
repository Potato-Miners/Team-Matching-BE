package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByUserAccount_UserId(String userId, Pageable pageable);

    void deleteByIdAndPost_IdAndUserAccount_UserId(Long commentId, Long postId, String userId);

}
