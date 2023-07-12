package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.CommentDto;
import com.teammatching.demo.domain.dto.TeamMatchPrincipal;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "댓글 API", description = "댓글 관련 도메인 API")
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "댓글 쓰기",
            description = "추가할 댓글 정보를 받아 댓글을 작성합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<Objects> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDto.CreateRequest request,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        commentService.createComment(request.toDto(postId, principal.toDto()));
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_CREATE_COMMENT)
                .build();
    }

    @Operation(
            summary = "댓글 수정",
            description = "수정할 댓글 정보를 받아 댓글을 수정합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{commentId}")
    public ResponseResult<Objects> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto.UpdateRequest request,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        commentService.updateComment(commentId, request.toDto(postId, principal.toDto()));
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_UPDATE_COMMENT)
                .build();
    }

    @Operation(
            summary = "댓글 삭제",
            description = "삭제할 댓글의 ID를 받아 댓글을 삭제합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{commentId}")
    public ResponseResult<Objects> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        commentService.deleteComment(commentId, postId, principal.userId());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_DELETE_COMMENT)
                .build();
    }
}
