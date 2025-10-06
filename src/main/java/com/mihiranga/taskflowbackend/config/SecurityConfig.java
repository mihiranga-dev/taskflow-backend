package com.mihiranga.taskflowbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) for our stateless API
                .csrf(AbstractHttpConfigurer::disable)

                // Define the authorization rules for HTTP requests
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to the H2 console and our auth endpoints
                        .requestMatchers("/h2-console/**", "/api/auth/**").permitAll()
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )

                // The H2 console runs in a frame, so we need to disable frame options
                // This is a common requirement for the H2 console
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}