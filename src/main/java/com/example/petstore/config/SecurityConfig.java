package com.example.petstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable all security and allow public access to all paths
        return http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Allow public access to Swagger UI and API docs
                                // Allow public access to other necessary API paths
                                // All other requests are also public
                                .anyRequest().permitAll()
                ) // Allow all requests without authentication
                .formLogin(formLogin -> formLogin.disable())  // Disable form login (not necessary)
                .httpBasic(httpBasic -> httpBasic.disable()) // Disable HTTP Basic authentication (not necessary)
                .build();
    }
}
