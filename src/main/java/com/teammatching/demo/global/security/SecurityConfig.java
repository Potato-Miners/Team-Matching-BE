package com.teammatching.demo.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammatching.demo.domain.repository.UserAccountRepository;
import com.teammatching.demo.global.security.filter.jwt.JwtAuthenticationProcessingFilter;
import com.teammatching.demo.global.security.filter.login.CustomJsonUsernamePasswordAuthenticationFilter;
import com.teammatching.demo.global.security.handler.LoginFailureHandler;
import com.teammatching.demo.global.security.handler.LoginSuccessHandler;
import com.teammatching.demo.application.UserAccount.LoginService;
import com.teammatching.demo.global.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserAccountRepository userAccountRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .antMatchers(
                                "/sign-up", "/login", "/logout",
                                "/swagger-ui.html", "/v1/api-docs/**", "/swagger-ui/**",
                                "/api/r/**"
                        ).permitAll()
                        //.anyRequest().authenticated()
                )
                .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userAccountRepository,objectMapper);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler(objectMapper);
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter filter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler());
        filter.setAuthenticationFailureHandler(loginFailureHandler());
        return filter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userAccountRepository, redisTemplate);
    }
}
