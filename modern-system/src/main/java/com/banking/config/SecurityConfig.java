package com.banking.config;

import com.banking.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authz -> authz
                    // Allow public access to public pages, login, static resources
                    .requestMatchers("/", "/login", "/about", "/news", "/contact",
                            "/css/**", "/js/**", "/images/**", "/actuator/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureUrl("/login?error")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            )
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/test/**", "/api/migration/**")
            )
            .userDetailsService(userDetailsService);

    return http.build();
}
}