package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioRequestDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.RegistroDiario;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistroDiarioController {

    private final RegistroDiarioRepository registroRepo;
    private final PacienteRepository pacienteRepo;

    @PostMapping
    public ResponseEntity<RegistroDiarioResponseDTO> criar(
            @RequestBody @Valid RegistroDiarioRequestDTO dto
    ) {
        Paciente paciente = pacienteRepo.findByEmail(dto.emailPaciente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Paciente não encontrado para o e-mail informado."
                ));

        LocalDate data = (dto.dataRegistro() != null && !dto.dataRegistro().isBlank())
                ? LocalDate.parse(dto.dataRegistro())
                : LocalDate.now();

        RegistroDiario registro = RegistroDiario.builder()
                .dataRegistro(data)
                .descricaoDia(dto.descricaoDia())
                .moodDoDia(dto.moodDoDia())
                .nivelEstresse(dto.nivelEstresse())
                .qualidadeSono(dto.qualidadeSono())
                .atividadeFisica(dto.atividadeFisica())
                .motivoGratidao(dto.motivoGratidao())
                .paciente(paciente)
                .build();

        RegistroDiario salvo = registroRepo.save(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(salvo));
    }

    @GetMapping("/paciente/{email}")
    public List<RegistroDiarioResponseDTO> listarPorPaciente(@PathVariable String email) {
        return registroRepo.findByPacienteEmailOrderByDataRegistroDesc(email)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDiarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody RegistroDiarioRequestDTO dto
    ) {
        RegistroDiario registro = registroRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Registro não encontrado."
                ));

        if (dto.dataRegistro() != null && !dto.dataRegistro().isBlank()) {
            registro.setDataRegistro(LocalDate.parse(dto.dataRegistro()));
        }
        if (dto.descricaoDia() != null) {
            registro.setDescricaoDia(dto.descricaoDia());
        }
        if (dto.moodDoDia() != null) {
            registro.setMoodDoDia(dto.moodDoDia());
        }
        if (dto.nivelEstresse() != null) {
            registro.setNivelEstresse(dto.nivelEstresse());
        }
        if (dto.qualidadeSono() != null) {
            registro.setQualidadeSono(dto.qualidadeSono());
        }
        if (dto.atividadeFisica() != null) {
            registro.setAtividadeFisica(dto.atividadeFisica());
        }
        if (dto.motivoGratidao() != null) {
            registro.setMotivoGratidao(dto.motivoGratidao());
        }

        RegistroDiario atualizado = registroRepo.save(registro);
        return ResponseEntity.ok(toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!registroRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado.");
        }
        registroRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas")
    public List<AlertaRegistroDTO> listarAlertasCriticos() {
        List<RegistroDiario> todos = registroRepo.findAll();

        Map<Long, AlertaRegistroDTO> alertaPorPaciente = new LinkedHashMap<>();

        for (RegistroDiario r : todos) {
            if (!contemPalavrasDeRisco(r.getDescricaoDia())) {
                continue;
            }

            Paciente p = r.getPaciente();
            if (p == null) continue;

            Long pacienteId = p.getId();
            // Garante 1 alerta por paciente
            if (!alertaPorPaciente.containsKey(pacienteId)) {
                alertaPorPaciente.put(
                        pacienteId,
                        new AlertaRegistroDTO(
                                pacienteId,
                                p.getNome(),
                                p.getTelefone(),
                                r.getMoodDoDia(),
                                r.getDescricaoDia()
                        )
                );
            }
        }

        return new ArrayList<>(alertaPorPaciente.values());
    }

    private boolean contemPalavrasDeRisco(String descricao) {
        if (descricao == null || descricao.isBlank()) return false;

        String texto = descricao.toLowerCase();

        String[] riscos = new String[]{
                "suicidio",
                "suicídio",
                "me matar",
                "me matar.",
                "quero morrer",
                "quero morrer.",
                "não quero mais viver",
                "nao quero mais viver",
                "tirar minha vida",
                "acabar com tudo",
                "acabar com a minha vida",
                "morrer",
                "morte"
        };

        for (String palavra : riscos) {
            if (texto.contains(palavra)) {
                return true;
            }
        }
        return false;
    }

    private RegistroDiarioResponseDTO toResponse(RegistroDiario r) {
        return new RegistroDiarioResponseDTO(
                r.getId(),
                r.getDataRegistro(),
                r.getDescricaoDia(),
                r.getMoodDoDia(),
                r.getNivelEstresse(),
                r.getQualidadeSono(),
                r.getAtividadeFisica(),
                r.getMotivoGratidao()
        );
    }
}
