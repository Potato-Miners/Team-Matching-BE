package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.AdmissionRepository;
import com.teammatching.demo.domain.repository.TeamRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final TeamRepository teamRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Page<AdmissionDto> getSimpleAdmission(Long teamId, String adminId, Pageable pageable) {
        Team team = teamRepository.getReferenceById(teamId);
        if (team.getAdminId().equals(adminId)) {
            return admissionRepository.findByTeam_Id(teamId, pageable)
                    .map(AdmissionDto::from);
        } else {
            throw new RuntimeException("팀의 관리자만 접근할 수 있습니다.");
        }
    }

    @Transactional(readOnly = true)
    public AdmissionDto getAdmissionByUserId(Long teamId, String userId, String adminId) {
        Team team = teamRepository.getReferenceById(teamId);
        if (team.getAdminId().equals(adminId)) {
            return AdmissionDto.from(admissionRepository.findByUserAccount_UserId(userId));
        } else {
            throw new RuntimeException("팀의 관리자만 접근할 수 있습니다.");
        }
    }

    public void applyTeam(AdmissionDto request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.userAccountDto().userId());
        Team team = teamRepository.getReferenceById(request.teamId());
        if (admissionRepository.findByUserAccountAndTeam(userAccount, team)) {
            throw new RuntimeException("이미 가입신청된 팀입니다.");
        } else if (team.getAdminId().equals(userAccount.getUserId())) {
            throw new RuntimeException("이미 가입된 팀입니다.");
        } else {
            admissionRepository.save(request.toEntity(userAccount, team));
        }
    }
}
