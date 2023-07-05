package com.teammatching.demo.web.handler;

import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.web.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
        log.info("Login Success!! userId : {}", userId);
        log.info("Login Success!! AccessToken : {}", accessToken);
        log.info("AccessToken Expiration Period : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
