package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/", "/menu", "/menu/productos/**", "/menu/mas-pedidos", "/menu/clasicos",
                                "/user/**", "/nosotros", "/contacto", "/css/**", "/img/**", "/js/**", "/log-in")
                        .permitAll()
                         .requestMatchers("/admin/pedidos", "/admin/pedidos/**").hasAnyAuthority("CLIENT", "ADMIN", "EMPLOYEE")
                        .requestMatchers("/admin/historial/**", "/admin/pedidos/**")
                        .hasAnyAuthority("EMPLOYEE", "ADMIN")

                        // Luego ADMIN para todo lo demás
                        .requestMatchers("/admin/categorias/**", "/admin/productos/**", "/admin/tiendas/**",
                                "/admin/usuarios/**", "/admin/extras/**", "/admin/**")
                        .hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(
                        login -> login.loginPage("/log-in")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                    // Lógica de redirección por rol
                                    if (authentication.getAuthorities().stream()
                                            .anyMatch(g -> g.getAuthority().equals("ADMIN"))) {
                                        response.sendRedirect("/admin/productos");
                                    } else {
                                        response.sendRedirect("/");
                                    }
                                })
                                .failureHandler((request, response, exception) -> {
                                    String errorMessage;
                                    if (exception instanceof DisabledException) {
                                        errorMessage = "Cuenta desactivada. Contacte al administrador.";
                                    } else if (exception instanceof BadCredentialsException) {
                                        errorMessage = "Usuario o contraseña incorrectos";
                                    } else {
                                        errorMessage = "Error en el inicio de sesión";
                                    }

                                    request.getSession().setAttribute("errorMessage", errorMessage);
                                    response.sendRedirect("/login?error");
                                }))
                .logout(
                        logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

}
