package com.teammatching.demo.web.service.login;

import com.teammatching.demo.domain.entity.UserAccount;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param userId the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(RuntimeException::new);    //TODO: 예외 처리 구현 필요

        return User.builder()
                .username(userAccount.getUserId())
                .password(userAccount.getUserPassword())
                .roles(userAccount.getRole().name())
                .build();
    }
}
