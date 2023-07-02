package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.CommentDto;
import com.teammatching.demo.domain.dto.PostDto;
import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.UserAccountDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
            @PathVariable("userId") String userId
    ) {
        return ResponseResult.<UserAccountDto>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyPage(userId, UserAccountDto.builder().build().userId()))      //TODO: 인증 정보 필요
                .build();
    }

    @PatchMapping
    public ResponseResult<Objects> updateAccount(
            @PathVariable("userId") String userId,
            @RequestBody UserAccountDto.UpdateRequest request
    ) {
        myPageService.updateAccount(userId, request.toDto(), UserAccountDto.builder().build().userId());     //TODO: 인증 정보 필요
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }

    @GetMapping("/posts")
    public ResponseResult<Page<PostDto.SimpleResponse>> getMyPosts(
            @PathVariable("userId") String userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<PostDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyPosts(userId, UserAccountDto.builder().build().userId(), pageable)
                        .map(PostDto.SimpleResponse::from))     //TODO: 인증 정보 필요
                .build();
    }

    @GetMapping("/comments")
    public ResponseResult<Page<CommentDto.SimpleResponse>> getMyComments(
            @PathVariable("userId") String userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<CommentDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyComments(userId, UserAccountDto.builder().build().userId(), pageable)
                        .map(CommentDto.SimpleResponse::from))  //TODO: 인증 정보 필요
                .build();
    }

    @GetMapping("/teams")
    public ResponseResult<Page<TeamDto.SimpleResponse>> getMyTeams(
            @PathVariable("userId") String userId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyTeams(userId, UserAccountDto.builder().build().userId(), pageable)
                        .map(TeamDto.SimpleResponse::from))     //TODO: 인증 정보 필요
                .build();
    }
}
