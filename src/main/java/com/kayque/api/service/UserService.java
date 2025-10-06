package com.kayque.api.service;

import ch.qos.logback.core.net.LoginAuthenticator;
import com.kayque.api.config.SecurityConfig;
import com.kayque.api.entities.User;
import com.kayque.api.entities.dto.LoginRequestDTO;
import com.kayque.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, User user){
        return !securityConfig.bCryptPasswordEncoder().matches(loginRequestDTO.password, user.getPassword());
    }

    public JwtClaimsSet buildClaims(User user, Long expiresIn){
        return JwtClaimsSet
                .builder()
                .issuer("backend")
                .subject(user.getUserName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .build();
    }
}
