package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByAdminId(String userId, Pageable pageable);

    void deleteByIdAndAdminId(Long teamId, String adminId);
}
