package com.meal.meal_ops.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // public
                        .requestMatchers(
                                "/auth/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // restaurants + menu
                        .requestMatchers("/restaurants/**")
                        .hasAnyRole("ADMIN", "MANAGER", "MEMBER")

                        .requestMatchers(HttpMethod.POST, "/restaurants/*/menu")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.GET, "/restaurants/*/menu")
                        .hasAnyRole("ADMIN", "MANAGER", "MEMBER")

                        // orders
                        .requestMatchers(HttpMethod.GET, "/orders/*")
                        .hasAnyRole("ADMIN", "MANAGER", "MEMBER")

                        .requestMatchers(HttpMethod.POST, "/orders/create")
                        .hasAnyRole("ADMIN", "MANAGER", "MEMBER")

                        .requestMatchers(HttpMethod.POST, "/orders/*/checkout")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.POST, "/orders/cancel/*")
                        .hasAnyRole("ADMIN","MANAGER")

                        .requestMatchers(HttpMethod.GET, "/orders")
                        .hasAnyRole("ADMIN","MANAGER","MEMBER")

                        // payment
                        .requestMatchers(HttpMethod.GET,"/orders/payment/**")
                        .hasAnyRole("ADMIN","MANAGER","MEMBER")
                        .requestMatchers(HttpMethod.PUT,"/orders/payment/**")
                        .hasRole("ADMIN")


                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
