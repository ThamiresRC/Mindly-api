package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.RegistroDiarioRequestDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.RegistroDiario;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes/{pacienteId}/registros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistroDiarioController {

    private final RegistroDiarioRepository registroDiarioRepository;
    private final PacienteRepository pacienteRepository;

    // CREATE - registrar o dia do paciente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroDiarioResponseDTO criar(@PathVariable Long pacienteId,
                                           @RequestBody @Valid RegistroDiarioRequestDTO dto) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));

        RegistroDiario entity = new RegistroDiario();
        entity.setPaciente(paciente);
        entity.setData(dto.data() != null ? dto.data() : LocalDate.now());
        entity.setEmocao(dto.emocao());
        entity.setComeuBem(dto.comeuBem());
        entity.setDormiuBem(dto.dormiuBem());
        entity.setRelatoDoDia(dto.relatoDoDia());
        entity.setDesabafo(dto.desabafo());

        RegistroDiario salvo = registroDiarioRepository.save(entity);
        return toResponse(salvo);
    }

    // READ - listar registros do paciente (mais recentes primeiro)
    @GetMapping
    public List<RegistroDiarioResponseDTO> listar(@PathVariable Long pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado");
        }

        return registroDiarioRepository.findByPacienteIdOrderByDataDesc(pacienteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // (Opcional) buscar um registro específico
    @GetMapping("/{registroId}")
    public RegistroDiarioResponseDTO buscarPorId(@PathVariable Long pacienteId,
                                                 @PathVariable Long registroId) {
        RegistroDiario registro = registroDiarioRepository.findById(registroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        if (!registro.getPaciente().getId().equals(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registro não pertence a este paciente");
        }

        return toResponse(registro);
    }

    private RegistroDiarioResponseDTO toResponse(RegistroDiario r) {
        boolean alerta = r.getDesabafo() != null &&
                r.getDesabafo().toLowerCase().contains("matar");

        return new RegistroDiarioResponseDTO(
                r.getId(),
                r.getPaciente().getId(),
                r.getData(),
                r.getEmocao(),
                r.getComeuBem(),
                r.getDormiuBem(),
                r.getRelatoDoDia(),
                r.getDesabafo(),
                alerta
        );
    }
}
