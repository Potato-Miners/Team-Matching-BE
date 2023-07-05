package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Page<Admission> findByTeam_Id(Long teamId, Pageable pageable);

    Admission findByUserAccount_UserId(String userId);

    Optional<Admission> findByUserAccountAndTeam(UserAccount userAccount, Team team);

}
