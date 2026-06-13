package com.gabriel.smartroad.dto.User;

public record ResponseDTO (
        Long id,
        String name,
        String email
        // Não retorna a senha do usuario para o front.
){
}
