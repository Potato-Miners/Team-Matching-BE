package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.Admission;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdmissionDto(
        Long id,
        String application,
        Boolean approval,
        Long teamId,
        UserAccountDto userAccountDto,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static AdmissionDto from(Admission entity) {
        return AdmissionDto.builder()
                .id(entity.getId())
                .application(entity.getApplication())
                .approval(entity.getApproval())
                .teamId(entity.getTeam().getId())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .build();
    }

    public Admission toEntity(UserAccount userAccount, Team team) {
        return Admission.builder()
                .application(application)
                .approval(approval)
                .userAccount(userAccount)
                .team(team)
                .build();
    }

    @Builder
    public record SimpleResponse(
            Long id,
            String userId
    ) {
        public static SimpleResponse from(AdmissionDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .userId(dto.userAccountDto().userId())
                    .build();
        }
    }

    public record CreateRequest(
            String application
    ) {
        public AdmissionDto toDto(Long teamId, UserAccountDto userAccountDto) {
            return AdmissionDto.builder()
                    .application(application)
                    .approval(false)
                    .teamId(teamId)
                    .userAccountDto(userAccountDto)
                    .build();
        }
    }
}
