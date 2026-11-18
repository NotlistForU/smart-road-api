package calamadevs.controller;

import calamadevs.dto.Authentic.RequestForgotPassword;
import calamadevs.dto.Authentic.RequestResetPassword;
import calamadevs.dto.Authentic.MessageResponse;
import calamadevs.dto.Authentic.ResponseAuthentic;
import calamadevs.dto.Authentic.LoginRequest;
import calamadevs.dto.Authentic.RegisterUserRequest;
import calamadevs.service.AuthenticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticController {

    private final AuthenticService authenticService;

    @GetMapping("/ping")
    public String ping() {
        log.info("A new request has arrived on the /ping route");
        return "PONG! O Controller está vivo!";
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid RegisterUserRequest dto) {
        authenticService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Registration completed successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthentic> login(@RequestBody @Valid LoginRequest dto) {
        ResponseAuthentic responseAuthentic = authenticService.login(dto.email(), dto.password());
        return ResponseEntity.ok(responseAuthentic);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody @Valid RequestForgotPassword dto) {

        String email = dto.email();
        authenticService.requestRecovery(email);

        return ResponseEntity
                .ok(new MessageResponse("If the email exists in the system, an access code will be sent to that email address."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody @Valid RequestResetPassword dto) {
        String token = dto.token();
        String newPassword = dto.newPassword();

        authenticService.resetPassword(token, newPassword);

        return ResponseEntity.ok(new MessageResponse("Password reset successfully! You can log in now"));
    }
}
