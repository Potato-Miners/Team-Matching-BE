package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Team;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record TeamDto(
        Long id,
        String name,
        String description,
        String hashtag,
        Set<AdmissionDto> admissionDtos,

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
                .admissionDtos(
                        entity.getAdmissions().stream()
                                .map(AdmissionDto::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new))
                )
                .hashtag(entity.getHashtag())
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
                .build();
    }

    public record SimpleResponse(
            Long id,
            String name,
            String hashtag
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .id(id)
                    .name(name)
                    .hashtag(hashtag)
                    .build();
        }
    }

    public record CreateRequest(
            String name,
            String description,
            String hashtag
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .hashtag(hashtag)
                    .build();
        }
    }

    public record UpdateRequest(
            String name,
            String description,
            String hashtag
    ) {
        public TeamDto toDto() {
            return TeamDto.builder()
                    .name(name)
                    .description(description)
                    .hashtag(hashtag)
                    .build();
        }
    }
}
