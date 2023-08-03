package com.teammatching.demo.web.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Getter
@Service
public class JwtService {

    @Value("{jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERID_CLAIM = "userId";
    private static final String BEARER = "Bearer ";

    private final UserAccountRepository userAccountRepository;

    public String createAccessToken(String userId) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(USERID_CLAIM, userId)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);

        log.info("Reissued AccessToken : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);

        log.info("AccessToken, RefreshToken Header configuration complete");
    }

    public Optional<String> extractToken(HttpServletRequest request, String header) {
        return Optional.ofNullable(request.getHeader(header))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""));
    }

    public Optional<String> extractClaim(String accessToken, String claim) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(claim)
                    .asString());
        } catch (Exception e) {
            log.error("Invalid AccessToken!");
            return Optional.empty();
        }
    }

    public void updateRefreshToken(String userId, String refreshToken) {
        userAccountRepository.findById(userId)
                .ifPresentOrElse(
                        userAccount -> userAccount.updateRefreshToken(refreshToken),
                        NotFoundException.UserAccount::new
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid Token!! {}", e.getMessage());
            return false;
        }
    }

    public Long getExpiration(String accessToken) {
        Date expiration = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(accessToken).getExpiresAt();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }
}
