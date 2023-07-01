package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    void deleteByIdAndAdminId(Long teamId, String adminId);
}
