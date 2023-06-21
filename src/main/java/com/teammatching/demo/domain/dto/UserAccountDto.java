package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserAccountDto(
        Long id,
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto from(UserAccount entity) {
        return UserAccountDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .userPassword(entity.getUserPassword())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public UserAccount toEntity() {
        return UserAccount.builder()
                .userId(userId)
                .userPassword(userPassword)
                .email(email)
                .nickname(nickname)
                .memo(memo)
                .build();
    }

    public record SignUpRequest(
            String userId,
            String userPassword,
            String email,
            String nickname
    ) {
        public UserAccountDto toDto() {
            return UserAccountDto.builder()
                    .userId(userId)
                    .userPassword(userPassword)
                    .email(email)
                    .nickname(nickname)
                    .build();
        }
    }

    public record LoginRequest(
            String userId,
            String userPassword
    ) {
    }

}
