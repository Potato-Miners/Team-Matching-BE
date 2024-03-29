package com.teammatching.demo.global.exception;

import com.auth0.jwt.exceptions.*;
import com.teammatching.demo.global.customResponse.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    //409
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseResult<Object> alreadyExistsException(AlreadyExistsException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.CONFLICT.value())
                .resultMessage("이미 존재하는 " + e.getMessage() + "입니다.")
                .build();
    }

    //404
    @ExceptionHandler(NullCheckException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult<Object> nullCheckException(NullCheckException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultMessage("해당 파라미터는 Null일 수 없습니다. : " + e.getMessage())
                .build();
    }

    //401
    @ExceptionHandler(NotEqualsException.UserAccount.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> userAccountNotEqualsException(NotEqualsException.UserAccount e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("유저 정보가 일치하지 않습니다.")
                .build();
    }

    //401
    @ExceptionHandler(NotEqualsException.TeamAdmin.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> teamAdminNotEqualsException(NotEqualsException.TeamAdmin e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("팀의 관리자가 아니므로 접근할 수 없습니다.")
                .build();
    }

    //401
    @ExceptionHandler(NotEqualsException.Password.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> teamAdminNotEqualsException(NotEqualsException.Password e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("비밀번호와 비밀번호 확인이 다릅니다.")
                .build();
    }

    //401
    @ExceptionHandler(NotEqualsException.PasswordValid.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> teamAdminNotEqualsException(NotEqualsException.PasswordValid e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("비밀번호가 틀립니다.")
                .build();
    }

    //401
    @ExceptionHandler(NullTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> nullTokenException(NullTokenException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("인증 토큰이 없습니다.")
                .build();
    }

    //401
    @ExceptionHandler({
            JWTVerificationException.class,
            AlgorithmMismatchException.class,
            TokenExpiredException.class,
            SignatureVerificationException.class,
            MissingClaimException.class,
            IncorrectClaimException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> jwtVerificationException(JWTVerificationException e) {
        String resultMessage = e.getMessage();
        if (e instanceof TokenExpiredException) resultMessage = "인증 토큰이 만료되었습니다.";
        if (e instanceof AlgorithmMismatchException) resultMessage = "인증 토큰의 알고리즘이 정의된 것과 다릅니다.";
        if (e instanceof SignatureVerificationException) resultMessage = "인증 토큰의 서명이 유효하지 않습니다.";
        if (e instanceof MissingClaimException) resultMessage = "인증 토큰의 권한이 없습니다.";
        if (e instanceof IncorrectClaimException) resultMessage = "인증 토큰의 권한이 일치하지 않습니다.";

        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage(resultMessage)
                .build();
    }

    //401
    @ExceptionHandler({
            NotFoundException.class,
            NotFoundException.UserAccount.class,
            NotFoundException.Admission.class,
            NotFoundException.Comment.class,
            NotFoundException.Post.class,
            NotFoundException.Team.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> notFoundException(NotFoundException e) {
        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage("해당 " + e.getMessage() + "(을)를 찾을 수 없습니다.")
                .build();
    }

    //401
    @ExceptionHandler({
            AuthenticationException.class,
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseResult<Object> authenticationException(AuthenticationException e) {
        String resultMessage = e.getMessage();
        if (e instanceof InternalAuthenticationServiceException) resultMessage = "존재하지 않는 아이디입니다.";
        if (e instanceof BadCredentialsException) resultMessage = "비밀번호가 맞지 않습니다.";

        return ResponseResult.builder()
                .resultCode(HttpStatus.UNAUTHORIZED.value())
                .resultMessage(resultMessage)
                .build();
    }

    //409
    @ExceptionHandler({
            TeamJoinException.class,
            TeamJoinException.AlreadyJoined.class,
            TeamJoinException.AlreadyApplying.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseResult<Object> teamJoinException(TeamJoinException e) {
        String resultMessage = e.getMessage();
        if(e instanceof TeamJoinException.AlreadyJoined) resultMessage = "이미 가입된 팀입니다.";
        if(e instanceof TeamJoinException.AlreadyApplying) resultMessage = "이미 가입 신청된 팀입니다.";
        if(e instanceof TeamJoinException.AlreadyApproved) resultMessage = "이미 승인된 요청입니다.";
        if(e instanceof TeamJoinException.FullCapacity) resultMessage = "더 이상 팀원을 추가할 수 없습니다.";

        return ResponseResult.builder()
                .resultCode(HttpStatus.CONFLICT.value())
                .resultMessage(resultMessage)
                .build();
    }
}
