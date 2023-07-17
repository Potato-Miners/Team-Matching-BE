package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.AdmissionRepository;
import com.teammatching.demo.domain.repository.TeamRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.NotEqualsException;
import com.teammatching.demo.result.exception.NullCheckException;
import com.teammatching.demo.result.exception.TeamJoinException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final TeamRepository teamRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<AdmissionDto> getAdmissions() {
        return admissionRepository.findAll().stream()
                .map(AdmissionDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AdmissionDto> getSimpleAdmission(Long teamId, String adminId, Pageable pageable) {
        Team team = teamRepository.getReferenceById(teamId);
        if (team.getAdminId().equals(adminId)) {
            return admissionRepository.findByTeam_Id(teamId, pageable)
                    .map(AdmissionDto::from);
        } else {
            throw new NotEqualsException.TeamAdmin();
        }
    }

    @Transactional(readOnly = true)
    public AdmissionDto getAdmissionByUserId(Long teamId, String userId, String adminId) {
        Team team = teamRepository.getReferenceById(teamId);
        if (team.getAdminId().equals(adminId)) {
            return AdmissionDto.from(admissionRepository.findByUserAccount_UserId(userId));
        } else {
            throw new NotEqualsException.TeamAdmin();
        }
    }

    public void applyTeam(AdmissionDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Team team = teamRepository.getReferenceById(request.teamId());
        if (admissionRepository.findByUserAccountAndTeam(userAccount, team).isPresent()) {
            throw new TeamJoinException.AlreadyApplying();
        }

        if (team.getAdminId().equals(userAccount.getUserId())) {
            throw new TeamJoinException.AlreadyJoined();
        }

        if (request.approval() == null) throw new NullCheckException("가입 신청서");
        admissionRepository.save(request.toEntity(userAccount, team));
    }

}
