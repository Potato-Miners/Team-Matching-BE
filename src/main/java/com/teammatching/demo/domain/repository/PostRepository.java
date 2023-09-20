package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p from Post p WHERE " +
            "p.userAccount.userId LIKE %:keyword% OR " +
            "p.title LIKE %:keyword% OR " +
            "p.hashtag LIKE %:keyword%")
    Page<Post> searchAllByFields(@Param("keyword") String searchKeyword, Pageable pageable);

    Page<Post> findByUserAccount_UserId(String userId, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long postId, String userId);
}
