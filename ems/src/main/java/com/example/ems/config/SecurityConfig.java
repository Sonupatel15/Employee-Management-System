package com.example.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/employees/**").permitAll() // Restrict access to HR & Managers
                        .requestMatchers("/managers/**").permitAll()

                        .requestMatchers("/projects/**").permitAll()// Only HR can manage managers
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }
}
