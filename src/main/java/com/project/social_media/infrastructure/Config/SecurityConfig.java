package com.project.social_media.infrastructure.Config;

import com.project.social_media.infrastructure.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // TODO: Add admin role? maybe?

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true); // Accept for cookie
                    return config;
                }))

                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for testing

                .sessionManagement((session) ->
                        // This is fucking important, so don't fucking touch it, is for the JWT
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((request) ->
                                request
                                // Permit all requests >> For Testing Purpose
//                                .anyRequest().permitAll() // Uncomment this for testing
                                        // >> Files
                                        .requestMatchers("/uploads/**").permitAll()

                                        // >> Websocket
                                        // Unauthenticated guest user socket for all GET method
                                        .requestMatchers("/ws/**").permitAll()
                                        // Authenticated guest user socket

                                        // >> TODO: Admin Page ?
                                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                                        // >> Authentication
                                        .requestMatchers("/api/v1/auth/**").permitAll()

                                        // Guest has access to all GET Method
                                        .requestMatchers(HttpMethod.GET,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**",
                                                "/api/v1/relationships/**",
                                                "/api/v1/friend-requests/**"
                                        ).permitAll()

                                        // Guest has to Authenticated to use Controller
                                        // Temp disable for testing
                                        .requestMatchers(HttpMethod.GET,
                                                "/api/v1/messages/**"
                                        ).authenticated()

                                        .requestMatchers(HttpMethod.POST,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**",
                                                "/api/v1/relationships/**",
                                                "/api/v1/friend-requests/**"
                                        ).authenticated()

                                        .requestMatchers(HttpMethod.PUT,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**",
                                                "/api/v1/relationships/**",
                                                "/api/v1/friend-requests/**"
                                        ).authenticated()


                                        .requestMatchers(HttpMethod.DELETE,
                                                "/api/v1/users/**",
                                                "/api/v1/posts/**",
                                                "/api/v1/comments/**",
                                                "/api/v1/likes/**",
                                                "/api/v1/relationships/**",
                                                "/api/v1/friend-requests/**"
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

            // Ignore public API
            String path = request.getRequestURI();
            if (path.startsWith("/api/v1/auth/register") ||
                    path.startsWith("/api/v1/auth/login") ||
                    path.startsWith("/api/v1/auth/refresh")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Get access token from cookie
            String token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("accessToken".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // If access token from cookie not found, check in header Authorization ( for API testing )
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            // Debug Print
            // System.out.println("Token from cookie or header: " + token);

            if (token != null && JwtUtil.validateToken(token)) {
                try {
                    Claims claims = JwtUtil.extractAllClaims(token);
                    String username = claims.getSubject();
                    String role = claims.get("role").toString();

                    // Debug Print
                    // System.out.println("Username from token: " + username);
                    // System.out.println("Role from token: " + role);

                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + role)
                    );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // System.out.println("Set authentication: " + SecurityContextHolder.getContext().getAuthentication());
                } catch (Exception e) {
                    System.out.println("Error processing token: " + e.getMessage());
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                System.out.println("No valid token found");
            }

            filterChain.doFilter(request, response);
        }
    }
}
