package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record PostWithCommentDto(
        Long id,
        String title,
        String content,
        String hashtag,
        UserAccountDto userAccountDto,
        Set<CommentDto> commentDtos,

        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static PostWithCommentDto from(Post entity) {
        return PostWithCommentDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .hashtag(entity.getHashtag())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .commentDtos(
                        entity.getComments().stream()
                                .map(CommentDto::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new))
                )
                .build();
    }
}
