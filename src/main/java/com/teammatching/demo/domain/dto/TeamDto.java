package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Team;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TeamDto(
        Long id,
        String name,
        String description,
        String hashtag,
        Long max,

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
                .hashtag(entity.getHashtag())
                .max(entity.getMax())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Team toEntity() {
        return Team.builder()
                .name(name)
                .description(description)
                .hashtag(hashtag)
                .max(max)
                .build();
    }
}
