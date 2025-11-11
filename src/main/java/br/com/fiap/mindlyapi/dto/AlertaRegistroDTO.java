package br.com.fiap.mindlyapi.dto;

public record AlertaRegistroDTO(
        Long pacienteId,
        String pacienteNome,
        String telefone,
        String emocao,
        String desabafo
) {
}
