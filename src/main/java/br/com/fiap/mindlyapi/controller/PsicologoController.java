package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import br.com.fiap.mindlyapi.dto.PsicologoRequestDTO;
import br.com.fiap.mindlyapi.dto.PsicologoResponseDTO;
import br.com.fiap.mindlyapi.model.Psicologo;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/psicologos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PsicologoController {

    private final PsicologoRepository psicologoRepository;
    private final RegistroDiarioRepository registroDiarioRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PsicologoResponseDTO criar(@RequestBody @Valid PsicologoRequestDTO dto) {
        Psicologo p = new Psicologo();
        p.setNome(dto.nome());
        p.setEmail(dto.email());
        p.setSenha(dto.senha());
        p.setTelefone(dto.telefone());

        Psicologo salvo = psicologoRepository.save(p);
        return toResponse(salvo);
    }

    @GetMapping
    public List<PsicologoResponseDTO> listar() {
        return psicologoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PsicologoResponseDTO buscarPorId(@PathVariable Long id) {
        Psicologo p = psicologoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Psicólogo não encontrado"));
        return toResponse(p);
    }

    @GetMapping("/alertas")
    public List<AlertaRegistroDTO> listarRegistrosComAlerta() {
        return registroDiarioRepository.findAll()
                .stream()
                .filter(r -> r.getDescricaoDia() != null &&
                        r.getDescricaoDia().toLowerCase().contains("matar"))
                .map(r -> new AlertaRegistroDTO(
                        r.getPaciente().getId(),
                        r.getPaciente().getNome(),
                        r.getPaciente().getTelefone(),
                        r.getMoodDoDia(),
                        r.getDescricaoDia()))
                .toList();
    }

    private PsicologoResponseDTO toResponse(Psicologo p) {
        return new PsicologoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getEmail());
    }
}
