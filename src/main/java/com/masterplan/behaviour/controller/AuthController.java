package com.masterplan.behaviour.controller;

import org.springframework.web.bind.annotation.RestController;

import com.masterplan.behaviour.response.APIResponse;
import com.masterplan.behaviour.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<APIResponse> token(Authentication authentication) {

        if(authentication==null){
            APIResponse response = new APIResponse(
                HttpStatus.BAD_REQUEST.value(), 
                "Username and Password are required Fields!", 
                null
            );
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        
        //LOG.debug("Token request for the user: {}",authentication.getName());

        String token = tokenService.generateToken(authentication);
        LOG.debug("Token Granted: {}", token);
        APIResponse response = new APIResponse(
            HttpStatus.OK.value(), 
            "Successfully Login!", 
            token
        );
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
}
