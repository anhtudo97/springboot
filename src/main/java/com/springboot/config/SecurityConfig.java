package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // active web security
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(formLogin -> formLogin.loginProcessingUrl("/login").permitAll());

        http.authorizeHttpRequests(req -> req
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register")
                        .permitAll()
//                .requestMatchers("/v1/admin/vip").hasRole("ADMIN")
//                .requestMatchers("/v1/admin/normal").hasRole("USER")
                        .anyRequest().authenticated()
        );

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin")
//                .roles("admin", "user") // ROLE_ADMIN
                .authorities("ROLE_ADMIN")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password("{noop}user") // raw
//                .roles("USER")
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
