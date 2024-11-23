package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SpringConfig(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/users/**",
                                    "/auth/**")
                            .permitAll();
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl(
                                "http://localhost:8080/api/oauth2/callback/google",
                                true)
                        .failureUrl("/login?error=true"))
                .formLogin(form -> form
                        .defaultSuccessUrl(
                                "http://localhost:8080/doc/swagger-ui/index.html",
                                true)
                        .failureUrl("/login?error=true"))
                .requiresChannel(channel -> channel.anyRequest().requiresInsecure());

        return http.build();
    }

    @Autowired
    @Profile("!test")
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder); // Usa el campo passwordEncoder directamente aquí.
        return authenticationManagerBuilder.build();
    }
}
