package com.teammatching.demo.application.Admission;

import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.AdmissionRepository;
import com.teammatching.demo.domain.repository.TeamRepository;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.global.exception.NotEqualsException;
import com.teammatching.demo.global.exception.NotFoundException;
import com.teammatching.demo.global.exception.NullCheckException;
import com.teammatching.demo.global.exception.TeamJoinException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
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
        if (team.getAdminUserAccount().getUserId().equals(adminId)) {
            return admissionRepository.findByTeam_IdWithoutAdmin(teamId, adminId, pageable)
                    .map(AdmissionDto::from);
        } else {
            throw new NotEqualsException.TeamAdmin();
        }
    }

    @Transactional(readOnly = true)
    public AdmissionDto getAdmissionByUserId(Long teamId, Long admissionId, String adminId) {
        Team team = teamRepository.getReferenceById(teamId);
        if (team.getAdminUserAccount().getUserId().equals(adminId)) {
            return AdmissionDto.from(admissionRepository.findById(admissionId)
                    .orElseThrow(NotFoundException.Admission::new));
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

        if (team.getAdminUserAccount().getUserId().equals(userAccount.getUserId())) {
            throw new TeamJoinException.AlreadyJoined();
        }

        if (request.approval() == null) throw new NullCheckException("가입 신청서");
        admissionRepository.save(request.toEntity(userAccount, team));
    }

    public void approvalAdmission(Long teamId, Long admissionId, String adminId) {
        Team approvalTeam = teamRepository.getReferenceById(teamId);
        Admission admission = admissionRepository.getReferenceById(admissionId);
        if (admission.getApproval()) {
            throw new TeamJoinException.AlreadyApproved();
        }
        if(!approvalTeam.getAdminUserAccount().getUserId().equals(adminId)){
            throw new NotFoundException.UserAccount();
        }
        if(approvalTeam.getCapacity().equals(approvalTeam.getTotal())){
            throw new TeamJoinException.FullCapacity();
        }
        admission.setApproval(true);
        approvalTeam.setTotal(approvalTeam.getTotal() + 1);
    }

    public void rejectAdmission(Long teamId, Long admissionId, String adminId) {
        Team cancelTeam = teamRepository.getReferenceById(teamId);
        Admission admission = admissionRepository.getReferenceById(admissionId);
        if (cancelTeam.getAdminUserAccount().getUserId().equals(adminId)) {
            admissionRepository.deleteById(admission.getId());
        } else {
            throw new NotFoundException.UserAccount();
        }
    }

    public void cancelAdmission(Long teamId, Long admissionId, String userId){
        Admission admission = admissionRepository.getReferenceById(admissionId);
        if (admission.getUserAccount().getUserId().equals(userId)) {
            admissionRepository.deleteById(admission.getId());
        } else {
            throw new NotFoundException.UserAccount();
        }
    }
}
