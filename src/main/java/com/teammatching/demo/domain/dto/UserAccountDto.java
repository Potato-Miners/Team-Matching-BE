package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(name = "UserAccountDto(유저 Dto)")
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

    @Schema(name = "UserAccountDto.SignUpRequest(유저 회원가입 요청 Dto)")
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

    @Schema(name = "UserAccountDto.LoginRequest(유저 로그인 요청 Dto)")
    public record LoginRequest(
            String userId,
            String userPassword
    ) {
    }

    @Schema(name = "UserAccountDto.UpdateRequest(유저 수정 요청 Dto)")
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
