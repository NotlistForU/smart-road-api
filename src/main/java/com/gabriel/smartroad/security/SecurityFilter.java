package com.gabriel.smartroad.security;

import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("URI: " + request.getRequestURI());
        // 1. Pega o token do request (ex: "Bearer eyJhbG...")
        String token = this.recoverToken(request);
        System.out.println("TOKEN: " + token);
        if (token != null) {
            // 2. Manda o TokenService validar e devolver o e-mail
            String email = tokenService.validateToken(token);
            System.out.println("EMAIL: " + email);

            if (!email.isEmpty()) {
                // 3. Busca o usuário no banco de dados pelo e-mail
                User user = userRepository.findByEmail(email).orElse(null);
                System.out.println("USER: " + user);
                if (user != null) {
                    // 4. Cria a "Ficha de Aprovação" (Authentication) e avisa o Spring que ele tá logado!
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("AUTHENTICATE!");
                }
            }
        }
        // 5. Manda a requisição seguir o fluxo dela (ir para o Controller)
        filterChain.doFilter(request, response);
    }

    // Função para tirar a palavra Bearer do token.
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}
