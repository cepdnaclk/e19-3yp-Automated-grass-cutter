package com.example.grasscutter.MobileApplication.Auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://cognito-idp.ap-south-1.amazonaws.com/ap-south-1_SQstNYOKe/.well-known/jwks.json").build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz.requestMatchers("/users/*", "/users/signin")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

}