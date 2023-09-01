package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    void deleteByIdAndAdminUserAccount_UserId(Long teamId, String adminId);
}
