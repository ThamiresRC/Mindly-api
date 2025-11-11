package br.com.fiap.mindlyapi.dto;

public record PacienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String observacao
) {
}
