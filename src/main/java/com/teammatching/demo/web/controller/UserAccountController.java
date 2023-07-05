package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/sign-up")
    public ResponseResult<Objects> signUp(
            @RequestBody UserAccountDto.SignUpRequest request
    ) {
        userAccountService.signUp(request.toDto());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }
}
