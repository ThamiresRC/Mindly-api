package br.com.fiap.mindlyapi.model;

import jakarta.persistence.*;
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

    private LocalDate dataRegistro;

    @Column(length = 500, nullable = false)
    private String descricaoDia;

    @Column(length = 100)
    private String moodDoDia;

    private Integer nivelEstresse;
    private Integer qualidadeSono;

    @Column(length = 50)
    private String atividadeFisica;

    @Column(length = 255)
    private String motivoGratidao;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}
