package com.teammatching.demo.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.global.customResponse.ResponseResult;
import com.teammatching.demo.global.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    /**
     * Calls the parent class {@code handle()} method to forward or redirect to the target
     * URL, and then calls {@code clearAuthenticationAttributes()} to remove any leftover
     * session data.
     *
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String userId = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(userId);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        userAccountRepository.findById(userId)
                .ifPresent(userAccount -> {
                    userAccount.updateRefreshToken(refreshToken);
                    userAccountRepository.saveAndFlush(userAccount);
                });

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        ResponseResult<Object> responseResult = ResponseResult.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage("로그인에 성공하였습니다.")
                .build();

        String result = objectMapper.writeValueAsString(responseResult);

        response.getWriter().write(result);

        log.info("Login Success!! userId : {}", userId);
        log.info("Login Success!! AccessToken : {}", accessToken);
        log.info("AccessToken Expiration Period : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
