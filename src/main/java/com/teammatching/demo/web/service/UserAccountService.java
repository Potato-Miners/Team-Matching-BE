package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.result.exception.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

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
}
