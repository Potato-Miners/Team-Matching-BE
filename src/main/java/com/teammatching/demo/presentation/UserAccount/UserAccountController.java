package com.teammatching.demo.presentation.UserAccount;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.global.customResponse.ResponseMessage;
import com.teammatching.demo.global.customResponse.ResponseResult;
import com.teammatching.demo.application.UserAccount.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "인증 API", description = "인증/인가 관련 API")
@RequestMapping("/auth")
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

    @Operation(
            summary = "로그아웃",
            description = "회원 정보를 받아 로그아웃합니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    public ResponseResult<Object> logout(
            @RequestBody UserAccountDto.LogoutRequest request
    ) {
        userAccountService.logout(request.accessToken(), request.userId());
        return ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(ResponseMessage.SUCCESS_LOGOUT)
                .build();
    }

}
