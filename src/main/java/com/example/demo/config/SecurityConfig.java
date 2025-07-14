package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.auth.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService service;

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            auth -> auth.requestMatchers("/", "/menu", "/menu/productos/**", "/menu/mas-pedidos", "/menu/clasicos", "/user/registro", "/nosotros", "/contacto", "/css/**", "/img/**", "/js/**", "/log-in")
            .permitAll()
            .requestMatchers("/admin/categorias/**", "/admin/productos/**", "/admin/tiendas/**", "/admin/usuarios/**","/admin/extras/**", "/admin/pedidos/**", "/admin").hasAnyAuthority("ADMIN")
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
