package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.AlreadyExistsException;
import com.teammatching.demo.result.exception.NotFoundException;
import com.teammatching.demo.web.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<UserAccountDto> getUserAccounts() {
        return userAccountRepository.findAll().stream()
                .map(UserAccountDto::from)
                .collect(Collectors.toList());
    }

    public void signUp(UserAccountDto request) {
        if (userAccountRepository.findById(request.userId()).isPresent()) {
            throw new AlreadyExistsException("아이디");
        }
        if (userAccountRepository.findByEmail(request.email()).isPresent()) {
            throw new AlreadyExistsException("이메일");
        }
        if (userAccountRepository.findByNickname(request.nickname()).isPresent()) {
            throw new AlreadyExistsException("닉네임");
        }

        UserAccount userAccount = request.toEntity();
        userAccount.passwordEncode(passwordEncoder);
        userAccountRepository.save(userAccount);
    }

    public void updatePassword(String userId, UserAccountDto.UpdatePasswordRequest request, String authenticatedUserId) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(NotFoundException.UserAccount::new);
        if (userId.equals(authenticatedUserId)
                && request.password().equals(request.passwordCheck())) {
            userAccount.setUserPassword(passwordEncoder.encode(request.password()));
        }
    }

    public void logout(String accessToken, String userId) {
        log.info("로그아웃 로직 접근");
        log.info("accessToken: {}", accessToken);
        Long expiration = jwtService.getExpiration(accessToken);
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        userAccountRepository.findById(userId)
                .ifPresent(userAccount -> userAccount.setRefreshToken(null));
        log.info("로그아웃 로직 완료");
    }
}
