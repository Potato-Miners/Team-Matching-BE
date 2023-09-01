package com.teammatching.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teammatching.demo.domain.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(name = "PostWithCommentDto(게시물 Dto (댓글 정보도 함께))")
@Builder
public record PostWithCommentDto(
        Long id,
        String title,
        String content,
        String hashtag,
        UserAccountDto userAccountDto,
        Set<CommentDto> commentDtos,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
