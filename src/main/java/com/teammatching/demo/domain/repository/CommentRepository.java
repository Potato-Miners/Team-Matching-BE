package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByIdAndPost_IdAndUserAccount_UserId(Long commentId, Long postId, String userId);
}
