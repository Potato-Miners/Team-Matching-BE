package com.teammatching.demo.web.filter.jwt;

import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
import com.teammatching.demo.domain.dto.Principal;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.web.service.jwt.JwtService;
import com.teammatching.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String NO_CHECK_URL = "/auth/login";
    private static final String USERID_CLAIM = "userId";

    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("doFilterInternal 접근");
        String refreshToken = jwtService.extractToken(request, refreshHeader)
                .filter(jwtService::isTokenValid)
                .orElse(null);


        if (refreshToken != null) {
            log.info("checkRefreshTokenAndReIssueAccessToken 접근");
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        if (refreshToken == null) {
            log.info("checkAccessTokenAndAuthentication 접근");
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userAccountRepository.findByRefreshToken(refreshToken)
                .ifPresent(userAccount -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(userAccount);
                    jwtService.sendAccessAndRefreshToken(
                            response,
                            jwtService.createAccessToken(userAccount.getUserId()),
                            reIssuedRefreshToken
                    );
                });
    }

    private String reIssueRefreshToken(UserAccount userAccount) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        userAccount.updateRefreshToken(reIssuedRefreshToken);
        userAccountRepository.saveAndFlush(userAccount);
        return reIssuedRefreshToken;
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        jwtService.extractToken(request,accessHeader)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> {
                    validBlackToken(accessToken);
                    jwtService.extractClaim(accessToken, USERID_CLAIM)
                            .ifPresent(userId -> userAccountRepository.findById(userId)
                                    .ifPresent(this::saveAuthentication));
                });

//        jwtService.extractToken(request, accessHeader)
//                .filter(jwtService::isTokenValid)
//                .ifPresent(accessToken -> jwtService.extractClaim(accessToken, USERID_CLAIM)
//                        .ifPresent(userId -> userAccountRepository.findById(userId)
//                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(UserAccount userAccount) {
        String userPassword = userAccount.getUserPassword();
        if (userPassword == null) {
            userPassword = PasswordUtil.generateRandomPassword();
        }
        UserDetails userDetails = Principal.builder()
                .userId(userAccount.getUserId())
                .userPassword(userAccount.getUserPassword())
                .email(userAccount.getEmail())
                .nickname(userAccount.getNickname())
                .memo(userAccount.getMemo())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void validBlackToken(String accessToken) {
        String blackToken = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.hasText(blackToken)) {
            throw new RuntimeException("로그 아웃 처리된 토큰 입니다.");       //TODO: 예외 처리 필요
        }
    }
}
