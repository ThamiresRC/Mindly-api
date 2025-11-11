package br.com.fiap.mindlyapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "registros_diarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate data; // dia do registro

    @Enumerated(EnumType.STRING)
    @NotNull
    private Emocao emocao; // emoção principal

    private Boolean comeuBem;
    private Boolean dormiuBem;

    @Size(max = 2000)
    private String relatoDoDia;

    @Size(max = 2000)
    private String desabafo; // onde vamos ler "matar" futuramente

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
