package com.example.backend.config;

import com.example.backend.filters.JwtTokenFilter;
import com.example.backend.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/users/register", "/api/v1/users/login")
                        .permitAll()
                        .requestMatchers(GET,"/api/v1/doctors/**")
                        .permitAll()
                        .requestMatchers(GET,"/api/v1/hospitals/**")
                        .permitAll()
                        .requestMatchers("/api/v1/appointments/**")
                        .hasAnyRole(Role.ADMIN , Role.USER , Role.DOCTOR)
                        .requestMatchers(POST,"/api/v1/doctors/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(GET,"/api/v1/doctors?")
                        .hasAnyRole(Role.ADMIN,Role.USER)
                        .requestMatchers(GET,"/api/v1/img/*")
                        .permitAll()
                        .requestMatchers(DELETE,"/api/v1/doctors/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(POST,"/api/v1/hospitals/**")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(GET,"/api/v1/hospitals?")
                        .hasAnyRole(Role.ADMIN)
                        .requestMatchers(DELETE,"/api/v1/hospitals/**")
                        .hasAnyRole(Role.ADMIN)
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

