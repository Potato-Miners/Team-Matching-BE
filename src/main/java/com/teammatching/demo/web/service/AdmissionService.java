package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class AdmissionService {

    public Page<AdmissionDto> getSimpleAdmission(Long teamId, UserAccountDto userAccountDto) {
        return null;
    }

    public AdmissionDto getAdmissionByUserId(Long teamId, String userId, UserAccountDto userAccountDto) {
        return null;
    }

    public void applyTeam(Long teamId, String application, UserAccountDto build) {
    }
}
