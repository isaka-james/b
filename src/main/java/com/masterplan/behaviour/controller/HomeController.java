package com.masterplan.behaviour.controller;

import org.springframework.web.bind.annotation.RestController;

import com.masterplan.behaviour.model.User;
import com.masterplan.behaviour.repository.UserRepository;
import com.masterplan.behaviour.response.APIResponse;
import com.masterplan.behaviour.service.TokenService;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
public class HomeController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;
   
    @Autowired
    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @GetMapping
    public String home(Principal principal) {
        return "Welcome home! "+principal.getName();
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

        // Save the user
        userRepository.save(user);

        APIResponse responseSuccessful = new APIResponse(
            HttpStatus.OK.value(), 
            "Username is successfully Created!", 
            null
        );
        return new ResponseEntity<>(responseSuccessful,HttpStatus.OK);
    }

    @GetMapping("/user-info")
    public String getUserInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = tokenService.getUsernameFromToken(token);
        Collection<String> roles = tokenService.getRolesFromToken(token);

        return String.format("Username: %s, Roles: %s", username, String.join(", ", roles));
    }
    
    
}
