package com.teammatching.demo.domain.repository;

import com.teammatching.demo.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByNickname(String nickname);

    Optional<UserAccount> findByRefreshToken(String refreshToken);

}
