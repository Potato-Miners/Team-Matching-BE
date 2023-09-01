package com.teammatching.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teammatching.demo.domain.entity.Comment;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(name = "CommentDto(댓글 Dto)")
@Builder
public record CommentDto(
        Long id,
        String content,
        Long postId,
        UserAccountDto userAccountDto,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static CommentDto from(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .postId(entity.getPost().getId())
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .modifiedAt(entity.getModifiedAt())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    public Comment toEntity(Post post, UserAccount userAccount) {
        return Comment.builder()
                .content(content)
                .post(post)
                .userAccount(userAccount)
                .build();
    }

    @Schema(name = "CommentDto.CreateRequest(댓글 생성 요청 Dto)")
    public record CreateRequest(
            String content
    ) {
        public CommentDto toDto(Long postId, UserAccountDto userAccountDto) {
            return CommentDto.builder()
                    .content(content)
                    .postId(postId)
                    .userAccountDto(userAccountDto)
                    .build();
        }
    }

    @Schema(name = "CommentDto.UpdateRequest",description = "댓글 수정 요청 Dto")
    public record UpdateRequest(
            String content
    ) {
        public CommentDto toDto(Long postId, UserAccountDto userAccountDto) {
            return CommentDto.builder()
                    .content(content)
                    .postId(postId)
                    .userAccountDto(userAccountDto)
                    .build();
        }
    }

    @Schema(name = "CommentDto.SimpleResponse(댓글 간단 응답 Dto)")
    @Builder
    public record SimpleResponse(
            Long id,
            String content,
            Long postId
    ) {
        public static SimpleResponse from(CommentDto dto) {
            return SimpleResponse.builder()
                    .id(dto.id)
                    .content(dto.content)
                    .postId(dto.postId())
                    .build();
        }
    }

}
