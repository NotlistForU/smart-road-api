package com.gabriel.smartroad.dto.Authentic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestResetPasswordDTO(
        @NotBlank(message = "Token is required")
        String token,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must have at least 8 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        ) String newPassword
) {
}
