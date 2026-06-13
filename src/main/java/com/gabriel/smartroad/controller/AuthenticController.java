package com.gabriel.smartroad.controller;


import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.Authentic.RequestForgotPasswordDTO;
import com.gabriel.smartroad.dto.Authentic.RequestResetPasswordDTO;
import com.gabriel.smartroad.dto.Authentic.MessageResponseDTO;
import com.gabriel.smartroad.dto.Authentic.TokenResponseDTO;
import com.gabriel.smartroad.dto.User.LoginDTO;
import com.gabriel.smartroad.dto.User.RegisterDTO;
import com.gabriel.smartroad.repository.UserRepository;
import com.gabriel.smartroad.service.Authentic.AuthenticService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticController {

    private final AuthenticService authenticService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    AuthenticController(AuthenticService authenticService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticService = authenticService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/ping")
    public String ping() {
        System.out.println("O PING FOI ACESSADO!");
        return "PONG! O Controller está vivo!";
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@RequestBody @Valid RegisterDTO dto){
        log.info("A new request has arrived on the /request route");
        authenticService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO("Registration completed successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        String token = authenticService.login(dto.email(), dto.password());
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }


    // 1. ROTA DE SOLICITAR RECUPERAÇÃO (Envia o e-mail)
    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDTO> forgotPassword(@RequestBody @Valid RequestForgotPasswordDTO dto) {
        String email = dto.email();

        // Chama o service que gera o token e "envia o e-mail"
        String generatedToken = authenticService.requestRecovery(email);

        // Devolvemos uma mensagem de sucesso (e o token junto provisoriamente para você testar)
        return ResponseEntity.ok(new MessageResponseDTO("Recovery email sent successfully! (TESTE token: " + generatedToken + ")"));
    }

    // 2. ROTA DE REDEFINIR A SENHA (Usa o token e a nova senha)
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDTO> resetPassword(@RequestBody @Valid RequestResetPasswordDTO dto) {
        String token = dto.token();
        String newPassword = dto.newPassword();

        // Chama o service que valida o token e salva a nova senha.
        authenticService.resetPassword(token, newPassword);

        return ResponseEntity.ok(new MessageResponseDTO("Password reset successfully! You can log in now"));
    }
}
