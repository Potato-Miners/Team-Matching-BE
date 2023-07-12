package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.TeamDto;
import com.teammatching.demo.domain.dto.TeamMatchPrincipal;
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

import java.util.Objects;

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
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_GET_SIMPLE_TEAMS)
                .resultData(teamService.getSimpleTeams(pageable).map(TeamDto.SimpleResponse::from))
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
                .statusCode(HttpStatus.OK)
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
    public ResponseResult<Objects> createTeam(
            @RequestBody TeamDto.CreateRequest request,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        teamService.createTeam(request.toDto(), principal.userId());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_CREATE_TEAM)
                .build();
    }

    @Operation(
            summary = "팀 수정",
            description = "수정할 팀 정보를 받아 팀을 수정합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{teamId}")
    public ResponseResult<Objects> updateTeam(
            @PathVariable("teamId") Long teamId,
            @RequestBody TeamDto.UpdateRequest request,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        teamService.updateTeam(teamId, request.toDto(), principal.userId());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_UPDATE_TEAM)
                .build();
    }

    @Operation(
            summary = "팀 삭제",
            description = "삭제할 팀의 ID를 받아 해당 팀을 삭제합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{teamId}")
    public ResponseResult<Objects> deleteTeam(
            @PathVariable("teamId") Long teamId,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        teamService.deleteTeam(teamId, principal.userId());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS_DELETE_TEAM)
                .build();
    }
}
