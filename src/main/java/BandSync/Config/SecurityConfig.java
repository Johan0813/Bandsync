package BandSync.Config;

import BandSync.Service.Integrantes.CustomIntegrantesDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomIntegrantesDetailsService customIntegrantesDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/login")
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/ensayos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/ensayos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/ensayos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/ensayos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/presentaciones/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/presentaciones/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/presentaciones/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/presentaciones/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/instrumentos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/instrumentos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/instrumentos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/instrumentos/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/integrantes/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/integrantes/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/integrantes/**"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/integrantes/**"
                        ).permitAll()

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://127.0.0.1:5500",
                        "http://localhost:5500",
                        "https://bandsync-frontend.onrender.com"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}