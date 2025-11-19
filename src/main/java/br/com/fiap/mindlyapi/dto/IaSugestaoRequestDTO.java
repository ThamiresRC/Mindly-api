package br.com.fiap.mindlyapi.dto;

public record IaSugestaoRequestDTO(
        String moodDoDia,
        Integer nivelEstresse,
        String descricaoDia
) {
}
