package com.teammatching.demo.domain.dto;

import com.teammatching.demo.domain.entity.Comment;
import com.teammatching.demo.domain.entity.Post;
import com.teammatching.demo.domain.entity.UserAccount;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        Long id,
        String content,
        Long postId,
        UserAccountDto userAccountDto,

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

}
