package com.gabriel.smartroad.dto.User;

import jakarta.validation.constraints.Email;

public record UpdateDTO (
    String name,
    @Email(message = "Email format is invalid")
    String email
    // Troca de senha em logica separada!
){
}
