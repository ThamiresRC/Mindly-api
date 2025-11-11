package br.com.fiap.mindlyapi.dto;

import br.com.fiap.mindlyapi.model.Emocao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegistroDiarioRequestDTO(

        // pode ser null, se for, usamos a data de hoje
        LocalDate data,

        @NotNull(message = "Emoção é obrigatória")
        Emocao emocao,

        Boolean comeuBem,
        Boolean dormiuBem,

        @Size(max = 2000)
        String relatoDoDia,

        @Size(max = 2000)
        String desabafo
) {
}
