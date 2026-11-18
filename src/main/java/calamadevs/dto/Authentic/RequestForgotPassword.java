package calamadevs.dto.Authentic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestForgotPassword(
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    String email
){}
