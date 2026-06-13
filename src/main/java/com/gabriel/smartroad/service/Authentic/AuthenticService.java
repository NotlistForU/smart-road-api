package com.gabriel.smartroad.service.Authentic;

import com.gabriel.smartroad.domain.PasswordResetToken;
import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.User.RegisterDTO;
import com.gabriel.smartroad.repository.PasswordResetTokenRepository;
import com.gabriel.smartroad.repository.UserRepository;
import com.gabriel.smartroad.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticService {

    final private UserRepository userRepository;
    final private PasswordResetTokenRepository passwordResetTokenRepository;
    final private PasswordEncoder passwordEncoder;
    final private TokenService tokenService;
    final private AuthenticationManager authenticationManager;

    public AuthenticService(
            UserRepository userRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterDTO dto) {
//        log.info("Start email registration process: {}", dto.email());
        if(userRepository.findByEmail(dto.email()).isPresent()){
//            log.error("Registration attempt failed. Email address {} is Already in use.", dto.email());
            throw new RuntimeException("Error: The email already exists");
        }
        User newUser = new User();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(newUser);
        log.info("User {} successfully registered in the database", dto.name());
    }

    public String login(String email, String password){
        var userNamePassword = new UsernamePasswordAuthenticationToken(email, password);
        var auth =  authenticationManager.authenticate(userNamePassword);
        var user = (User) auth.getPrincipal();

        return tokenService.generateToken(user);
    }


    // 1. PASSO.
    public String requestRecovery(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail not found"));

        // Gera token aleatório
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        /* TODO: Futuramente, colocar código  para enviar um email de verdade.
         *   Por enquanto apenas imprimir no postman.
         */
        System.out.println("Simulate sending e-mail to: " + email);
        System.out.println("Redefine link: http//smartroad.com/reset?token=" + token);

        return token; // Provisóriamente para testes.
    }

    public void resetPassword(String token, String newPassword) {
        // Busca token no banco
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token / Not found"));

        // Verifica se ja passou dos 15 minutos
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            passwordResetTokenRepository.delete(resetToken);
            throw new RuntimeException("The token has expired. Please request recovery again.");
        }

        // Se o token tiver valido troca a senha do usuario.
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Apaga o token do banco
        passwordResetTokenRepository.delete(resetToken);
    }
}
