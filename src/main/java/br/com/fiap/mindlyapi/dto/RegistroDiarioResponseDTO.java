package br.com.fiap.mindlyapi.dto;

import br.com.fiap.mindlyapi.model.Emocao;

import java.time.LocalDate;

public record RegistroDiarioResponseDTO(
        Long id,
        Long pacienteId,
        LocalDate data,
        Emocao emocao,
        Boolean comeuBem,
        Boolean dormiuBem,
        String relatoDoDia,
        String desabafo,
        boolean alerta // true se encontrar "matar" no desabafo
) {
}
