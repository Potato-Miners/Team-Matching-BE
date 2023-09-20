package com.teammatching.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teammatching.demo.domain.Category;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(name = "TeamDto(팀 Dto)")
@Builder
public record TeamDto(
        Long id,
        String name,
        String description,
        Category category,
        String hashtag,
        Integer capacity,
        Integer total,
        LocalDateTime deadline,

        UserAccountDto adminUserAccountDto,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static TeamDto from(Team entity) {
        return TeamDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .hashtag(entity.getHashtag())
                .capacity(entity.getCapacity())
                .total(entity.getTotal())
                .deadline(entity.getDeadline())
                .adminUserAccountDto(UserAccountDto.from(entity.getAdminUserAccount()))
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Team toEntity(UserAccount userAccount) {
        return Team.builder()
                .adminUserAccount(userAccount)
                .name(name)
                .description(description)
                .category(category)
                .hashtag(hashtag)
                .capacity(capacity)
                .total(total)
                .deadline(deadline)
                .build();
    }

    @Schema(name = "TeamDto.SimpleResponse(팀 간단 응답 Dto)")
    @Builder
    public record SimpleResponse(
            Long id,
            UserAccountDto adminUserAccountDto,
            String name,
            Category category,
            String hashtag,
            Integer capacity,
            Integer total,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            LocalDateTime deadline,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            LocalDateTime createdAt
    ) {
        public static SimpleResponse from(TeamDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .adminUserAccountDto(dto.adminUserAccountDto)
                    .name(dto.name)
                    .category(dto.category)
                    .total(dto.total)
                    .deadline(dto.deadline)
                    .createdAt(dto.createdAt)
                    .build();
        }
    }

    @Schema(name = "TeamDto.CreateRequest(팀 생성 요청 Dto)")
    public record CreateRequest(
            String name,
            String description,
            Category category,
            String hashtag,
            Integer capacity,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            LocalDateTime deadline
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .category(category)
                    .hashtag(hashtag)
                    .capacity(capacity)
                    .total(1)
                    .deadline(deadline)
                    .build();
        }
    }

    @Schema(name = "TeamDto.UpdateRequest(팀 수정 요청 Dto)")
    public record UpdateRequest(
            String name,
            String description,
            Category category,
            String hashtag,
            Integer capacity,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
            LocalDateTime deadline
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .category(category)
                    .hashtag(hashtag)
                    .capacity(capacity)
                    .deadline(deadline)
                    .build();
        }
    }
}
