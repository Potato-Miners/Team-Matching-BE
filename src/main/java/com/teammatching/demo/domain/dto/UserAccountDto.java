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
        RoleType role,
        String refreshToken,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UserAccountDto from(UserAccount entity) {
        return UserAccountDto.builder()
                .userId(entity.getUserId())
                .userPassword(entity.getUserPassword())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .memo(entity.getMemo())
                .role(entity.getRole())
                .refreshToken(entity.getRefreshToken())
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
                .role(role)
                .refreshToken(refreshToken)
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
                    .role(RoleType.USER)
                    .build();
        }
    }

    public record LoginRequest(
            String userId,
            String userPassword
    ) {
    }

    public record UpdateRequest(
            String email,
            String nickname,
            String memo
    ) {
        public UserAccountDto toDto() {
            return UserAccountDto.builder()
                    .email(email)
                    .nickname(nickname)
                    .memo(memo)
                    .build();
        }
    }

}
