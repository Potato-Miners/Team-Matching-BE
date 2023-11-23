package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("SELECT a FROM Admission a WHERE a.team.id = ?1 AND a.userAccount.userId != ?2 AND a.approval = false")
    Page<Admission> findByTeam_IdWithoutAdmin(Long teamId, String adminId, Pageable pageable);

    Optional<Admission> findByUserAccountAndTeam(UserAccount userAccount, Team team);

    @Query("SELECT a FROM Admission a WHERE a.userAccount.userId = ?1 AND a.approval = true")
    Page<Admission> findMyTeams(String userId, Pageable pageable);

    @Query("SELECT a FROM Admission a WHERE a.userAccount.userId = ?1 AND a.approval = false")
    Page<Admission> findMyJudgingTeams(String userId, Pageable pageable);

}
