package br.com.fiap.mindlyapi.dto;

import java.time.LocalDate;

public record RegistroDiarioResponseDTO(
        Long id,
        LocalDate dataRegistro,
        String descricaoDia,
        String moodDoDia,
        Integer nivelEstresse,
        Integer qualidadeSono,
        String atividadeFisica,
        String motivoGratidao
) {}
