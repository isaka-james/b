package com.masterplan.behaviour.controller;

import org.springframework.web.bind.annotation.RestController;

import com.masterplan.behaviour.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AuthController {

    private final TokenService tokenService;

    private static final Logger LOG=LoggerFactory.getLogger(AuthController.class);
    
    public AuthController(TokenService tokenService){
        this.tokenService=tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        LOG.debug("Token request for the user: {}",authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token Granted: {}", token);
        return token;
    }
    
}
