package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDto(
        Long id,
        String title,
        String content,
        String hashtag,
        UserAccountDto userAccountDto,

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

    @Builder
    public record SimpleResponse(
            Long id,
            String title,
            String hashtag,
            String userId,
            String nickname,
            LocalDateTime createdAt
    ) {
        public static SimpleResponse from(PostDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .title(dto.title)
                    .hashtag(dto.hashtag)
                    .userId(dto.userAccountDto().userId())
                    .nickname(dto.userAccountDto.nickname())
                    .createdAt(dto.createdAt)
                    .build();
        }
    }

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
