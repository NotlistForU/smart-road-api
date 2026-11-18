package calamadevs.dto.Authentic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
                @NotBlank(message = "Name is required") String name,
                @NotBlank(message = "Email is required") @Email(message = "Email format is invalid") String email,
                @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must have at least 8 characters") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character") String password,
                /*
                 * (?=.*[a-z]): Exige pelo menos uma letra minúscula.
                 * (?=.*[A-Z]): Exige pelo menos uma letra maiúscula.
                 * (?=.*\\d): Exige pelo menos um número.
                 * (?=.*[@$!%*?&#]): Exige pelo menos um caractere especial dessa lista.
                 */

                Boolean communitySharingEnabled

) {
}
