package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {


    @Query("SELECT t from Team t WHERE " +
            "t.adminUserAccount.userId LIKE %:keyword% OR " +
            "t.name LIKE %:keyword% OR " +
            "t.hashtag LIKE %:keyword%")
    Page<Team> searchAllByFields(@Param("keyword") String searchKeyword, Pageable pageable);

    void deleteByIdAndAdminUserAccount_UserId(Long teamId, String adminId);
}
