package com.masterplan.behaviour.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import org.springframework.security.oauth2.jwt.Jwt;

@Service
public class TokenService {

    private final JwtEncoder encoder;
     private final JwtDecoder decoder;

    @Value("${jwt.issuer}")
    private String issuerName;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
        this.encoder=jwtEncoder;
        this.decoder=jwtDecoder;
        
    }

    public String generateToken(Authentication authentication){
        Instant now=Instant.now();

        String scope=authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" "));

        JwtClaimsSet claims=JwtClaimsSet.builder()
                            .issuer(issuerName)
                            .issuedAt(now)
                            .expiresAt(now.plus(expirationTime, ChronoUnit.SECONDS))
                            .subject(authentication.getName())
                            .claim("scope",scope)
                            .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt decodeToken(String token) {
        return this.decoder.decode(token);
    }

    public String getUsernameFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getSubject();  // Gets the subject (username)
    }

    public Collection<String> getRolesFromToken(String token) {
        Jwt jwt = decodeToken(token);
        return jwt.getClaimAsStringList("scope").stream()
                .collect(Collectors.toList());  // Assumes roles are stored in the "scope" claim
    }
}
