package com.awbd.myreviewer.config;

import com.awbd.myreviewer.services.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("mysql")
public class SecurityJpaConfig {
    private final JpaUserDetailsService userDetailsService;

    public SecurityJpaConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/articles/form").hasAnyRole("ADMIN", "WRITER")
                        .requestMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .requestMatchers("/articles/*").hasAnyRole("ADMIN", "REVIEWER", "WRITER")
                        .requestMatchers("/categories/form").hasAnyRole("ADMIN", "GUEST", "WRITER")
                        .requestMatchers("/categories/delete").hasAnyRole("ADMIN")
                        .requestMatchers("/reviews/*").hasAnyRole("ADMIN", "REVIEWER")
                        .anyRequest().authenticated()
                ).headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ).formLogin(formLogin ->
                formLogin
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/perform_login")
                 )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"))
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
