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
        String adminId,
        String name,
        String description,
        Category category,
        String hashtag,
        Integer capacity,
        Integer total,
        LocalDateTime deadline,

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
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Team toEntity(UserAccount userAccount) {
        return Team.builder()
                .adminId(userAccount.getUserId())
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
            String adminId,
            String name,
            Category category,
            String hashtag,

            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
            LocalDateTime deadline
    ) {
        public static SimpleResponse from(TeamDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .adminId(dto.adminId)
                    .name(dto.name)
                    .category(dto.category)
                    .hashtag(dto.hashtag)
                    .deadline(dto.deadline)
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

            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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

            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
