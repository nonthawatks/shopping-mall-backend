package com.example.meeboilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.meeboilerplate.config.token.TokenFilter;
import com.example.meeboilerplate.service.impl.TokenServiceImpl;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final TokenServiceImpl tokenService;

    public SecurityConfig(TokenServiceImpl tokenService) {
        this.tokenService = tokenService;
    }

    private final String[] PUBLIC = {
            "/v1/promotions/**",
            "/v1/promotions-conditions/**",
    };

    private static final String[] SWAGGER_PATHS = {
        "/swagger-ui.html",
        "/swagger-ui/**", // Include this alternative path as well
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**"
};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.cors(config -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowCredentials(true);
            cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
            cors.addAllowedHeader("*");
            cors.addAllowedMethod("GET");
            cors.addAllowedMethod("POST");
            cors.addAllowedMethod("PUT");
            cors.addAllowedMethod("DELETE");
            cors.addAllowedMethod("OPTIONS");

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", cors);

            config.configurationSource(source);
        }).csrf(AbstractHttpConfigurer::disable).sessionManagement(config -> {
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).authorizeHttpRequests(config -> {
            config.requestMatchers(SWAGGER_PATHS).permitAll();
            config.requestMatchers(PUBLIC).anonymous();
            config.anyRequest().authenticated();
        }).addFilterBefore(new TokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class).build();
    }
}