package com.masterplan.behaviour.controller;

import org.springframework.web.bind.annotation.RestController;

import com.masterplan.behaviour.model.User;
import com.masterplan.behaviour.repository.UserRepository;
import com.masterplan.behaviour.response.APIResponse;
import com.masterplan.behaviour.service.TokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class AuthController {

    private static final Logger LOG=LoggerFactory.getLogger(AuthController.class);
    
    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private TokenService tokenService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        

        String token = tokenService.generateToken(authentication);
        LOG.debug("Token Granted: {}", token);
        APIResponse response = new APIResponse(
            HttpStatus.OK.value(), 
            "Successfully Login!", 
            token
        );

        String username = tokenService.getUsernameFromToken(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new APIResponse(400, "User not found", null));
        }
        User userLoged = userOptional.get();
        userLoged.setLastLogin(LocalDateTime.now());
        userRepository.save(userLoged);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse>  registerUser(@RequestBody User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            APIResponse responseIsPresent = new APIResponse(
                HttpStatus.CONFLICT.value(), 
                "Username is already Present!", 
                null
            );
            return new ResponseEntity<>(responseIsPresent,HttpStatus.CONFLICT);
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the role (you can modify this depending on your needs)
        user.setRole("owner");  // Default role
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setBorn(user.getBorn());
        user.setGender(user.getGender());

        // Save the user
        userRepository.save(user);

        APIResponse responseSuccessful = new APIResponse(
            HttpStatus.OK.value(), 
            "Username is successfully Created!", 
            null
        );
        return new ResponseEntity<>(responseSuccessful,HttpStatus.OK);
    }
    
}
