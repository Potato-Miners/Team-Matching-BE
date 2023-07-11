package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.Category;
import com.teammatching.demo.domain.entity.Team;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Builder
    public record SimpleResponse(
            Long id,
            String adminId,
            String name,
            Category category,
            String hashtag,
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

    public record CreateRequest(
            String name,
            String description,
            Category category,
            String hashtag,
            LocalDateTime deadline
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .category(category)
                    .hashtag(hashtag)
                    .deadline(deadline)
                    .build();
        }
    }

    public record UpdateRequest(
            String name,
            String description,
            Category category,
            String hashtag,
            LocalDateTime deadline
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .category(category)
                    .hashtag(hashtag)
                    .deadline(deadline)
                    .build();
        }
    }
}
