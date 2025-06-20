package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            auth -> auth.requestMatchers("/", "/menu", "/menu/productos/**", "/menu/mas-pedidos", "/menu/clasicos", "/formulario", "/nosotros", "/contacto", "/css/**", "/img/**", "/js/**", "/log-in")
            .permitAll()
            .anyRequest().authenticated())
        .formLogin(
            login -> login.loginPage("/log-in")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/")
            .failureUrl("/log-in?errors")
        )
        .logout(
            logout -> logout.logoutSuccessUrl("/")
        );
        return http.build();
    } 

}
