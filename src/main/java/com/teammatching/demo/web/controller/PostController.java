package com.teammatching.demo.web.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.PostWithCommentDto;
import com.teammatching.demo.domain.dto.Principal;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API", description = "게시글 도메인 관련 API")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "게시글 리스트 간단 조회",
            description = "간단한 게시글 정보를 포함한 리스트를 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseResult<Page<PostDto.SimpleResponse>> getSimplePosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<PostDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_SIMPLE_POSTS)
                .resultData(postService.getSimplePosts(pageable).map(PostDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "단일 게시글 상세 조회(댓글도 함께)",
            description = "단일 게시글에 대한 상세 정보를 댓글과 함께 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}")
    public ResponseResult<PostWithCommentDto> getPostById(
            @PathVariable("postId") Long postId
    ) {
        return ResponseResult.<PostWithCommentDto>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_POST_BY_ID)
                .resultData(postService.getPostById(postId))
                .build();
    }

    @Operation(
            summary = "게시글 쓰기",
            description = "추가할 게시글 정보를 받아 게시글을 작성합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<Object> createPost(
            @RequestBody PostDto.CreateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        postService.createPost(request.toDto(principal.toDto()));
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_CREATE_POST)
                .build();
    }

    @Operation(
            summary = "게시글 수정",
            description = "수정할 게시글 정보를 받아 게시글을 수정합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{postId}")
    public ResponseResult<Object> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostDto.UpdateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        postService.updatePost(postId, request.toDto(principal.toDto()));
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_UPDATE_POST)
                .build();
    }

    @Operation(
            summary = "게시글 삭제",
            description = "삭제할 게시글의 ID를 받아 해당 게시글을 삭제합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    public ResponseResult<Object> deletePost(
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        postService.deletePost(postId, principal.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_DELETE_POST)
                .build();
    }
}
