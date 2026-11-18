package calamadevs.service;

import calamadevs.domain.PasswordResetToken;
import calamadevs.domain.User;
import calamadevs.dto.Authentic.RegisterUserRequest;
import calamadevs.dto.Authentic.ResponseAuthentic;
import calamadevs.repository.PasswordResetTokenRepository;
import calamadevs.repository.UserRepository;
import calamadevs.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticService {

    final private UserRepository userRepository;
    final private PasswordResetTokenRepository passwordResetTokenRepository;
    final private PasswordEncoder passwordEncoder;
    final private TokenService tokenService;
    final private AuthenticationManager authenticationManager;
    final private MailService mailService;

    public void register(RegisterUserRequest dto) {
        // log.info("Start email registration process: {}", dto.email());
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            // log.error("Registration attempt failed. Email address {} is Already in use.",
            // dto.email());
            throw new RuntimeException("Error: The email already exists");
        }
        User newUser = new User();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.setCommunitySharingEnabled(Boolean.TRUE.equals(dto.communitySharingEnabled()));
        userRepository.save(newUser);
        log.info("User {} successfully registered in the database", dto.name());
    }

    public ResponseAuthentic login(String email, String password) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManager.authenticate(userNamePassword);
        var user = (User) auth.getPrincipal();

        String token = tokenService.generateToken(user);

        return (new ResponseAuthentic(
                token,
                user.getName(),
                user.getEmail(),
                user.isCommunitySharingEnabled()

        ));
    }

    public void requestRecovery(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail not found"));

        // Gera token aleatório
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = passwordResetTokenRepository.findByUser(user)
                .orElse(new PasswordResetToken(token, user));

        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        passwordResetTokenRepository.save(resetToken);
        mailService.sendPasswordResetEmail(email, token);
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
