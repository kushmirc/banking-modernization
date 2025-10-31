package com.banking.config;


import com.banking.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
            .authorizeHttpRequests(authz -> authz
                // Allow public access to public pages, login, static resources
                    .requestMatchers("/", "/login", "/about", "/news", "/contact",
                            "/css/**", "/js/**", "/images/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error")
                    .permitAll()
            )
            .logout( logout -> logout
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