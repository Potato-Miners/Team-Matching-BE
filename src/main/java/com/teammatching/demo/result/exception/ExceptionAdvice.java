package com.teammatching.demo.result.exception;

import com.teammatching.demo.result.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseResult<Object> alreadyExistsException(AlreadyExistsException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.CONFLICT.value())
                .resultMessage("이미 존재하는 " + e.getMessage() + "입니다.")
                .build();
    }

    @ExceptionHandler(NullCheckException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> nullCheckException(NullCheckException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultMessage("해당 파라미터는 Null일 수 없습니다. : " + e.getMessage())
                .build();
    }

    @ExceptionHandler(NotEqualsException.UserAccount.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> userAccountNotEqualsException(NotEqualsException.UserAccount e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("유저 정보가 일치하지 않습니다.")
                .build();
    }
    @ExceptionHandler(NotEqualsException.TeamAdmin.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> teamAdminNotEqualsException(NotEqualsException.TeamAdmin e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("팀의 관리자가 아니므로 접근할 수 없습니다.")
                .build();
    }
}
