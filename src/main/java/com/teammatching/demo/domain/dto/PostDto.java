package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(name = "PostDto(게시물 Dto)")
@Builder
public record PostDto(
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

    public static PostDto from(Post entity) {
        return PostDto.builder()
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
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Post toEntity(UserAccount userAccount) {
        return Post.builder()
                .title(title)
                .content(content)
                .hashtag(hashtag)
                .userAccount(userAccount)
                .build();
    }

    @Schema(name = "PostDto.SimpleResponse(게시물 간단 응답 Dto)")
    @Builder
    public record SimpleResponse(
            Long id,
            String title,
            String hashtag,
            String userId,
            String nickname,
            Integer commentsCount,
            LocalDateTime createdAt
    ) {
        public static SimpleResponse from(PostDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .title(dto.title)
                    .hashtag(dto.hashtag)
                    .userId(dto.userAccountDto().userId())
                    .nickname(dto.userAccountDto.nickname())
                    .commentsCount(dto.commentDtos.size())
                    .createdAt(dto.createdAt)
                    .build();
        }
    }

    @Schema(name = "PostDto.DetailResponse(게시물 상세 응답 Dto)")
    @Builder
    public record DetailResponse(
            Long id,
            String title,
            String content,
            String hashtag,
            UserAccountDto userAccountDto,
            Set<CommentDto> commentDtos,
            String nickname,

            LocalDateTime createdAt
    ) {
        public static DetailResponse from(PostDto dto) {
            return DetailResponse.builder()
                    .id(dto.id)
                    .title(dto.title)
                    .content(dto.content)
                    .hashtag(dto.hashtag)
                    .userAccountDto(dto.userAccountDto)
                    .commentDtos(dto.commentDtos)
                    .createdAt(dto.createdAt)
                    .build();
        }
    }

    @Schema(name = "PostDto.CreateRequest(게시물 생성 요청 Dto)")
    public record CreateRequest(
            String title,
            String content,
            String hashtag
    ) {
        public PostDto toDto(UserAccountDto userAccountDto) {
            return PostDto.builder()
                    .title(title)
                    .content(content)
                    .hashtag(hashtag)
                    .userAccountDto(userAccountDto)
                    .build();
        }
    }

    @Schema(name = "PostDto.UpdateRequest(게시물 수정 요청 Dto)")
    public record UpdateRequest(
            String title,
            String content,
            String hashtag
    ) {
        public PostDto toDto(UserAccountDto userAccountDto) {
            return PostDto.builder()
                    .title(title)
                    .content(content)
                    .hashtag(hashtag)
                    .userAccountDto(userAccountDto)
                    .build();
        }
    }
}
