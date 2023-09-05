package com.teammatching.demo.web.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.teammatching.demo.domain.dto.*;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.MyPageService;
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

@Tag(name = "마이페이지 API", description = "마이페이지 관련 API")
@RequiredArgsConstructor
@RequestMapping("/my-page/{userId}")
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(
            summary = "마이페이지 조회",
            description = "유저의 정보를 제공합니다."
    )
    @GetMapping
    public ResponseResult<UserAccountDto> getMyPage(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        return ResponseResult.<UserAccountDto>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_MY_PAGE)
                .resultData(myPageService.getMyPage(userId, principal.userId()))
                .build();
    }

    @Operation(
            summary = "마이페이지 수정",
            description = "요청받은 유저의 정보를 수정합니다."
    )
    @PatchMapping
    public ResponseResult<Object> updateAccount(
            @PathVariable("userId") String userId,
            @RequestBody UserAccountDto.UpdateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        myPageService.updateAccount(userId, request.toDto(), principal.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_UPDATE_ACCOUNT)
                .build();
    }

    @Operation(
            summary = "마이페이지 게시글 조회",
            description = "유저가 작성한 게시글 리스트를 제공합니다."
    )
    @GetMapping("/posts")
    public ResponseResult<Page<PostDto.SimpleResponse>> getMyPosts(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        return ResponseResult.<Page<PostDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_MY_POSTS)
                .resultData(myPageService.getMyPosts(userId, principal.userId(), pageable)
                        .map(PostDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "마이페이지 댓글 조회",
            description = "유저가 작성한 댓글 리스트를 제공합니다."
    )
    @GetMapping("/comments")
    public ResponseResult<Page<CommentDto.SimpleResponse>> getMyComments(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        return ResponseResult.<Page<CommentDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_MY_COMMENTS)
                .resultData(myPageService.getMyComments(userId, principal.userId(), pageable)
                        .map(CommentDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "마이페이지 팀 조회",
            description = "유저가 속한 팀 리스트를 제공합니다."
    )
    @GetMapping("/teams")
    public ResponseResult<Page<TeamDto.SimpleResponse>> getMyTeams(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_MY_TEAMS)
                .resultData(myPageService.getMyTeams(userId, principal.userId(), pageable)
                        .map(TeamDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "마이페이지 신청 중인 팀 조회",
            description = "유저가 신청중인(가입이 되지 않은) 팀 리스트를 제공합니다."
    )
    @GetMapping("/teams")
    public ResponseResult<Page<TeamDto.SimpleResponse>> getMyJudgingTeams(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_MY_TEAMS)
                .resultData(myPageService.getMyJudgingTeams(userId, principal.userId(), pageable)
                        .map(TeamDto.SimpleResponse::from))
                .build();
    }
}
