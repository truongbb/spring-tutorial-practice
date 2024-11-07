package com.github.truongbb.jwtrefreshtoken.config;

import com.github.truongbb.jwtrefreshtoken.security.AuthTokenFilter;
import com.github.truongbb.jwtrefreshtoken.security.AuthenticationEntryPointJwt;
import com.github.truongbb.jwtrefreshtoken.security.CustomUserDetailsService;
import com.github.truongbb.jwtrefreshtoken.statics.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {

    CustomUserDetailsService userDetailsService;

    AuthenticationEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 Console

                        // user start
                        .requestMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/{id}").hasAnyAuthority(Roles.USER.toString(), Roles.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasAnyAuthority(Roles.ADMIN.toString())
                        // user end

                        // authentication start
                        .requestMatchers(
                                "/api/v1/authentications/refresh_token",
                                "/api/v1/authentications/logout"
                        ).authenticated()
                        .requestMatchers(
                                "/api/v1/authentications/login",
                                "/api/v1/authentications/registration"
                        ).permitAll() // Allow access to log-in, register
                        // authentication end

                        // account start
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/accounts/{id}/password").authenticated()
                        .requestMatchers(
                                "/api/v1/accounts/{id}/activations",
                                "/api/v1/accounts/{id}/activation_emails",
                                "/api/v1/accounts/password_forgotten_emails",
                                "/api/v1/accounts/{id}/password_forgotten"
                        ).permitAll()
                        // account end

                        .requestMatchers("/api/**").authenticated() // all other apis need authentication
                        .anyRequest().permitAll() // all thymeleaf, html page don't have to authenticate
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(unauthorizedHandler)
                )
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 Console
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF globally
        return http.build();
    }

}
