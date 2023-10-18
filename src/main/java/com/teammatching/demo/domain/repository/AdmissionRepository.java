package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("SELECT a FROM Admission a WHERE a.team.id = :teamId AND a.team.adminUserAccount.userId != :adminId")
    Page<Admission> findByTeam_IdWithoutAdmin(@Param("teamId") Long teamId, @Param("adminId") String adminId, Pageable pageable);

    Optional<Admission> findByUserAccountAndTeam(UserAccount userAccount, Team team);

    @Query("SELECT a FROM Admission a WHERE a.userAccount.userId = :userId AND a.approval != false")
    Page<Admission> findMyTeams(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT a FROM Admission a WHERE a.userAccount.userId = :userId AND a.approval = false")
    Page<Admission> findMyJudgingTeams(@Param("userId") String userId, Pageable pageable);

}
