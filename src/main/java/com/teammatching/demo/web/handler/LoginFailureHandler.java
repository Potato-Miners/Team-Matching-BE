package com.teammatching.demo.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.result.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    /**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise
     * returns a 401 error code.
     * <p>
     * If redirecting or forwarding, {@code saveException} will be called to cache the
     * exception for use in the target view.
     *
     * @param request
     * @param response
     * @param exception
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String resultMessage = "로그인에 실패하였습니다.";
        if (exception instanceof InternalAuthenticationServiceException) resultMessage = "존재하지 않는 아이디입니다.";
        if (exception instanceof BadCredentialsException) resultMessage = "비밀번호를 다시 확인해주세요.";

        ResponseResult<Object> responseResult = ResponseResult.builder()
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultMessage(resultMessage)
                .build();

        String result = objectMapper.writeValueAsString(responseResult);
        response.getWriter().write(result);

        log.info("Login Failed : {}", exception.getMessage());
    }
}
