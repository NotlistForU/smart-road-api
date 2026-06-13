package com.gabriel.smartroad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Liga o CORS (Cross-Origin Resource Sharing). Necessário para a comunicão de navegador x api
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Desliga a proteção contra ataque CSRF (pois quem vai nos proteger é o nosso Token (não se faz uso de Cookie))
                .csrf(csrf -> csrf.disable())
                // Avisa o Spring que a nossa API "Amnésia" (Stateless) e não guarda sessão que faz a API ser extremamente leve, rápida e barata de manter!
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // === AQUI FICAM AS REGRAS DA PORTA ===
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Rotas Livres (Qualquer um acessa sem token)
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/recovery").permitAll() // Sua rota de recuperar senha
                        .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll() // Gráficos do Site!
                        .requestMatchers(HttpMethod.GET, "/api/auth/ping").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/catalog/**").permitAll()

                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()

                        // 2. O Resto (Exige o Token validado)
                        .anyRequest().authenticated()
                )
                // Coloca o nosso SecurityFilter para rodar ANTES do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Exporta o Gerente de Autenticação para a gente poder usar lá no nosso AuthenticService
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Ensina o Spring a usar a criptografia BCrypt para as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração detalhada do CORS: "Quem pode entrar e o que podem fazer"
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite que o Flutter Web (Chrome) de qualquer porta acesse
        configuration.setAllowedOrigins(Arrays.asList("*")); // -> troca quando subir pra internet
        // Permite os verbos HTTP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUST", "DELETE", "OPTIONS"));
        // Permite enviar JSON e Token
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Aplica em TODAS as rotas da API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
