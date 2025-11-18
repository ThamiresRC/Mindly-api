package br.com.fiap.mindlyapi.dto;

public record TokenResponseDTO(
        String nome,
        String email,
        String tipoUsuario,
        String token
) {
}
