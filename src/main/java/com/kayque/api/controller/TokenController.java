package com.kayque.api.controller;

import com.kayque.api.entities.User;
import com.kayque.api.entities.dto.LoginRequestDTO;
import com.kayque.api.entities.dto.LoginResponseDTO;
import com.kayque.api.repository.UserRepository;
import com.kayque.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Optional<User> user = userRepository.findByUserName(loginRequest.userName);

        if(user.isPresent() && userService.isLoginCorrect(loginRequest, user.get())){
            throw new BadCredentialsException("Usuário ou senha incorreto");
        }


        Long expiresIn = 1200L;
        JwtClaimsSet claimsSet = userService.buildClaims(user.get(), expiresIn);

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }
}
