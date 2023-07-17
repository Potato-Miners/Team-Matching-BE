package com.teammatching.demo.web.controller.api;

import com.teammatching.demo.domain.dto.*;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Test Read API", description = "개발 중 더미 데이터 확인을 위한 Read API")
@RequiredArgsConstructor
@RequestMapping("/api/r")
@RestController
public class ReadApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final TeamService teamService;
    private final AdmissionService admissionService;
    private final UserAccountService userAccountService;

    @Operation(
            summary = "게시글 목록 전체 조회",
            description = "모든 게시글 리스트를 조회합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public ResponseResult<List<PostDto>> getPosts() {
        return ResponseResult.<List<PostDto>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(postService.getPosts())
                .build();
    }

    @Operation(
            summary = "댓글 목록 전체 조회",
            description = "모든 댓글 리스트를 조회합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments")
    public ResponseResult<List<CommentDto>> getComments() {
        return ResponseResult.<List<CommentDto>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(commentService.getComments())
                .build();
    }

    @Operation(
            summary = "가입 신청 목록 전체 조회",
            description = "모든 가입 신청 리스트를 조회합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admissions")
    public ResponseResult<List<AdmissionDto>> getAdmissions() {
        return ResponseResult.<List<AdmissionDto>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(admissionService.getAdmissions())
                .build();
    }

    @Operation(
            summary = "팀 목록 전체 조회",
            description = "모든 팀 리스트를 조회합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teams")
    public ResponseResult<List<TeamDto>> getTeams() {
        return ResponseResult.<List<TeamDto>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(teamService.getTeams())
                .build();
    }

    @Operation(
            summary = "유저 목록 전체 조회",
            description = "모든 유저 리스트를 조회합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userAccounts")
    public ResponseResult<List<UserAccountDto>> getUserAccounts() {
        return ResponseResult.<List<UserAccountDto>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(userAccountService.getUserAccounts())
                .build();
    }


}
