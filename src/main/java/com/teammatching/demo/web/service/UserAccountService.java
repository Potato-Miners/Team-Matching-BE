package com.teammatching.demo.web.service;

import com.teammatching.demo.domain.dto.UserAccountDto;
import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserAccountDto request) {
        if (userAccountRepository.findById(request.userId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userAccountRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        if (userAccountRepository.findByNickname(request.nickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        UserAccount userAccount = request.toEntity();
        userAccount.passwordEncode(passwordEncoder);
        userAccountRepository.save(userAccount);
    }
}
