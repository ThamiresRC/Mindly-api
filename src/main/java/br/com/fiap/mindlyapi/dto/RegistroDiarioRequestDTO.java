package br.com.fiap.mindlyapi.dto;

public record RegistroDiarioRequestDTO(
        String dataRegistro,
        String descricaoDia,
        String moodDoDia,
        Integer nivelEstresse,
        Integer qualidadeSono,
        String atividadeFisica,
        String motivoGratidao,
        String emailPaciente
) {}
