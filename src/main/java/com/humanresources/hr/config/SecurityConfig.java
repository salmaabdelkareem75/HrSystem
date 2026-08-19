package com.humanresources.hr.config;

import com.humanresources.hr.security.JwtAuthenticationFilter;
import com.humanresources.hr.service.Impi.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // ============ AUTH ============

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register"
                        )
                        .permitAll()


                        // ============ CANDIDATES ============

                        .requestMatchers(
                                HttpMethod.POST,
                                "/candidates"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/candidates"
                        )
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/candidates/{id}"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "HR",
                                "CANDIDATE"
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/candidates/{id}"
                        )
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/candidates/{id}"
                        )
                        .hasRole("CANDIDATE")


                        // ============ JOBS ============

                        .requestMatchers(
                                HttpMethod.GET,
                                "/jobs"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "HR",
                                "CANDIDATE"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/jobs/{id}"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "HR",
                                "CANDIDATE"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/jobs"
                        )
                        .hasRole("HR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/jobs/{id}"
                        )
                        .hasRole("HR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/jobs/{id}"
                        )
                        .hasRole("HR")


                        // ============ APPLICATIONS ============

                        .requestMatchers(
                                HttpMethod.GET,
                                "/applications"
                        )
                        .hasAnyRole("ADMIN", "HR")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/applications/{id}"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "HR",
                                "CANDIDATE"
                        )

                        .requestMatchers(
                                HttpMethod.POST,
                                "/applications"
                        )
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/applications/{id}"
                        )
                        .hasRole("CANDIDATE")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/applications/{id}"
                        )
                        .hasRole("CANDIDATE")


                        .anyRequest().authenticated()
                )

                .userDetailsService(userDetailsService)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}