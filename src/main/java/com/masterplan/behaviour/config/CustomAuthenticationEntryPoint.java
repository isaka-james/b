package com.masterplan.behaviour.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Set response status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Prepare custom JSON response
        Map<String, Object> data = new HashMap<>();
        data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        data.put("message", "Wrong Password or Username! Please try again.");
        data.put("error", authException.getMessage());
        
        // Write JSON response
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}
