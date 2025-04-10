package com.example.project_socialmedia.infrastructure.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // TODO: Filter UnAuthorize Action
    // TODO: If a "guest" do action that require "guest" information? redirect them to register/login
    // TODO: Add admin role ?

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for testing
                .sessionManagement((session) ->
                        // This is fucking important, so don't fucking touch it, it for the JWT
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorizeHttpRequests) ->
                        // Permit all requests >> For Testing Purpose
                        authorizeHttpRequests.anyRequest().permitAll());
        return http.build();
    }
}
