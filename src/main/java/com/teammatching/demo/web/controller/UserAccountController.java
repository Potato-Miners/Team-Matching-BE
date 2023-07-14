package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "인증 API", description = "인증/인가 관련 API")
@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Operation(
            summary = "회원가입",
            description = "회원 정보를 받아 회원 가입합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-up")
    public ResponseResult<Object> signUp(
            @RequestBody UserAccountDto.SignUpRequest request
    ) {
        userAccountService.signUp(request.toDto());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_SIGN_UP)
                .build();
    }
}
