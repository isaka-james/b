package com.masterplan.behaviour.config;

import com.masterplan.behaviour.repository.UserRepository;
import com.masterplan.behaviour.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;
    private final UserRepository userRepository;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(RsaKeyProperties rsaKeys, UserRepository userRepository, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.rsaKeys = rsaKeys;
        this.userRepository = userRepository;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeRequests(auth -> auth
                            .requestMatchers("/token", "/register").permitAll()
                            .anyRequest().authenticated()
                    )
                    .exceptionHandling(exceptionHandling -> 
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .httpBasic(withDefaults())
                    .build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new UserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<User> optionalUser = userRepository.findByUsername(username);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    return org.springframework.security.core.userdetails.User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(user.getUserRole())  // Assuming the role is stored as a simple string
                            .build();
                } else {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            }

            // Other methods can be implemented as no-op or throwing UnsupportedOperationException if not needed
            @Override
            public void createUser(UserDetails user) {
                throw new UnsupportedOperationException("Not implemented");
            }

            @Override
            public void updateUser(UserDetails user) {
                throw new UnsupportedOperationException("Not implemented");
            }

            @Override
            public void deleteUser(String username) {
                throw new UnsupportedOperationException("Not implemented");
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                throw new UnsupportedOperationException("Not implemented");
            }

            @Override
            public boolean userExists(String username) {
                return userRepository.findByUsername(username).isPresent();
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsManager userDetailsManager) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsManager);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
