package com.example.project_socialmedia.infrastructure.Config;

import com.example.project_socialmedia.infrastructure.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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
                        // This is fucking important, so don't fucking touch it, is for the JWT
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((request) ->
                                // Permit all requests >> For Testing Purpose
                                request
//                                .anyRequest().permitAll() // Uncomment this for testing
                                        // >> Files
                                        .requestMatchers("/uploads/**").permitAll()

                                        // >> Websocket
                                        // Unauthenticated guest user socket for all GET method
                                        .requestMatchers("/ws/**").permitAll()
                                        // Authenticated guest user socket

                                        // >> TODO: Admin Page ?
                                        .requestMatchers("api/v1/admin/**").hasRole("ADMIN")

                                        // >> Authentication
                                        .requestMatchers("api/v1/auth/**").permitAll()

                                        // Guest has access to all GET Method
                                        // TODO: Like
                                        .requestMatchers(HttpMethod.GET,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**"
                                        ).permitAll()

                                        // Guest has to Authenticated to use Controller
                                        .requestMatchers(HttpMethod.GET,
                                                "/api/v1/messages/**"
                                        ).authenticated()

                                        .requestMatchers(HttpMethod.POST,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**"
                                        ).authenticated()

                                        .requestMatchers(HttpMethod.PUT,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**"
                                        ).authenticated()


                                        .requestMatchers(HttpMethod.DELETE,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**"
                                        ).authenticated()
                );
        return http.build();
    }

    public static class JwtAuthenticationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        @Nonnull HttpServletResponse response,
                                        @Nonnull FilterChain filterChain)
                throws ServletException, IOException {

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                if (JwtUtil.validateToken(token)) {
                    try {
                        Claims claims = JwtUtil.extractAllClaims(token);
                        String username = claims.getSubject();
                        String role = claims.get("role").toString();

                        // Create a list of granted authorities (roles)
                        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role)); // Prefix "ROLE_"

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}
