package com.teammatching.demo.web.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.Principal;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.TeamService;
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


@Tag(name = "팀 API", description = "팀 관련 도메인 API")
@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

    private final TeamService teamService;

    @Operation(
            summary = "팀 리스트 간단 조회",
            description = "간단한 팀 정보를 포함한 리스트를 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseResult<Page<TeamDto.SimpleResponse>> getSimpleTeams(
            @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_SIMPLE_TEAMS)
                .resultData(teamService.getSimpleTeams(pageable))
                .build();
    }

    @Operation(
            summary = "팀 상세 조회",
            description = "단일 팀에 대한 상세 정보를 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{teamId}")
    public ResponseResult<TeamDto> getTeamById(
            @PathVariable("teamId") Long teamId
    ) {
        return ResponseResult.<TeamDto>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_TEAM_BY_ID)
                .resultData(teamService.getTeamById(teamId))
                .build();
    }

    @Operation(
            summary = "팀 만들기",
            description = "추가할 팀 정보를 받아 팀을 생성합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<Object> createTeam(
            @RequestBody TeamDto.CreateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        teamService.createTeam(request.toDto(), principal.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_CREATE_TEAM)
                .build();
    }

    @Operation(
            summary = "팀 수정",
            description = "수정할 팀 정보를 받아 팀을 수정합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{teamId}")
    public ResponseResult<Object> updateTeam(
            @PathVariable("teamId") Long teamId,
            @RequestBody TeamDto.UpdateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        teamService.updateTeam(teamId, request.toDto(), principal.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_UPDATE_TEAM)
                .build();
    }

    @Operation(
            summary = "팀 삭제",
            description = "삭제할 팀의 ID를 받아 해당 팀을 삭제합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{teamId}")
    public ResponseResult<Object> deleteTeam(
            @PathVariable("teamId") Long teamId,
            @AuthenticationPrincipal Principal principal
    ) {
        if (principal == null) throw new JWTVerificationException("인증 정보가 없습니다.");
        teamService.deleteTeam(teamId, principal.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_DELETE_TEAM)
                .build();
    }
}
