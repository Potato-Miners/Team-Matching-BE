package com.teammatching.demo.web.controller;


import com.teammatching.demo.domain.dto.AdmissionDto;
import com.teammatching.demo.domain.dto.Principal;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.AdmissionService;
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

@Tag(name = "가입 신청 API", description = "가입 신청 도메인 관련 API")
@RequiredArgsConstructor
@RequestMapping("/teams/{teamId}/admission")
@RestController
public class AdmissionController {

    private final AdmissionService admissionService;

    @Operation(
            summary = "팀 가입 신청 리스트 간단 조회 (팀 관리자)",
            description = "팀 관리자에게 가입 신청자 리스트를 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseResult<Page<AdmissionDto.SimpleResponse>> getSimpleAdmission(
            @PathVariable("teamId") Long teamId,
            @AuthenticationPrincipal Principal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<AdmissionDto.SimpleResponse>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_SIMPLE_ADMISSION)
                .resultData(admissionService.getSimpleAdmission(teamId, principal.userId(), pageable)
                        .map(AdmissionDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "팀 가입 신청서 상세 조회 (팀 관리자)",
            description = "팀 관리자에게 가입 신청자의 가입 신청 내용을 상세 제공합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public ResponseResult<AdmissionDto> getAdmissionByUserId(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal Principal principal
    ) {
        return ResponseResult.<AdmissionDto>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_GET_ADMISSION_BY_USER_ID)
                .resultData(admissionService.getAdmissionByUserId(teamId, userId, principal.userId()))
                .build();
    }

    @Operation(
            summary = "팀 가입 신청",
            description = "팀 가입 지원서 내용과 함께 가입을 신청합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<Object> applyTeam(
            @PathVariable("teamId") Long teamId,
            @RequestBody AdmissionDto.CreateRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        admissionService.applyTeam(request.toDto(teamId, principal.toDto()));
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_APPLY_TEAM)
                .build();
    }
}
