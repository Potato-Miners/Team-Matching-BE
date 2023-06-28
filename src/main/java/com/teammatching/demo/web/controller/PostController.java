package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


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
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(postService.getSimplePosts().map(PostDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "게시글 상세 조회",
            description = "단일 게시글에 대한 상세 정보를 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}")
    public ResponseResult<PostDto> getPostById(
            @PathVariable("postId") Long postId
    ) {
        return ResponseResult.<PostDto>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(postService.getPostById(postId))
                .build();
    }

    @Operation(
            summary = "게시글 쓰기",
            description = "추가할 게시글 정보를 받아 게시글을 작성합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<Objects> createPost(
            @RequestBody PostDto.CreateRequest request
    ) {
        postService.createPost(request.toDto(UserAccountDto.builder().build()));        //TODO: 인증 정보 필요
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }

    @Operation(
            summary = "게시글 수정",
            description = "수정할 게시글 정보를 받아 게시글을 수정합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{postId}")
    public ResponseResult<Objects> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostDto.UpdateRequest request
    ) {
        postService.updatePost(postId, request.toDto(UserAccountDto.builder().build()));        //TODO: 인증 정보 필요
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }

    @Operation(
            summary = "게시글 삭제",
            description = "삭제할 게시글의 ID를 받아 해당 게시글을 삭제합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    public ResponseResult<Objects> deletePost(
            @PathVariable("postId") Long postId
    ) {
        postService.deletePost(postId, UserAccountDto.builder().build());       //TODO: 인증 정보 필요
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }
}
