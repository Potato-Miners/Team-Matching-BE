package com.teammatching.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teammatching.demo.domain.entity.Comment;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(name = "CommentWithPostDto(댓글 Dto - 게시글과 함께)")
@Builder
public record CommentWithPostDto(
        Long id,
        String content,
        PostDto postDto,
        UserAccountDto userAccountDto,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {
    public static CommentWithPostDto from(Comment entity) {
        return CommentWithPostDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .postDto(PostDto.from(entity.getPost()))
                .userAccountDto(UserAccountDto.from(entity.getUserAccount()))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public Comment toEntity(Post post, UserAccount userAccount) {
        return Comment.builder()
                .content(content)
                .post(post)
                .userAccount(userAccount)
                .build();
    }
}
