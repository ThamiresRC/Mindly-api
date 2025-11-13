package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.dto.RegistroDiarioRequestDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioResponseDTO;
import br.com.fiap.mindlyapi.model.RegistroDiario;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroDiarioService {

    private final RegistroDiarioRepository registroRepo;

    public List<RegistroDiarioResponseDTO> listarPorPaciente(Long pacienteId) {
        return registroRepo.findByPacienteIdOrderByDataRegistroDesc(pacienteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RegistroDiarioResponseDTO atualizar(
            Long pacienteId,
            Long registroId,
            RegistroDiarioRequestDTO dto) {
        RegistroDiario r = registroRepo.findById(registroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        if (r.getPaciente() == null || !r.getPaciente().getId().equals(pacienteId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você só pode alterar seus próprios registros.");
        }

        if (dto.dataRegistro() != null && !dto.dataRegistro().isBlank()) {
            r.setDataRegistro(LocalDate.parse(dto.dataRegistro()));
        }
        if (dto.descricaoDia() != null) {
            r.setDescricaoDia(dto.descricaoDia());
        }
        if (dto.moodDoDia() != null) {
            r.setMoodDoDia(dto.moodDoDia());
        }
        if (dto.nivelEstresse() != null) {
            r.setNivelEstresse(dto.nivelEstresse());
        }
        if (dto.qualidadeSono() != null) {
            r.setQualidadeSono(dto.qualidadeSono());
        }
        if (dto.atividadeFisica() != null) {
            r.setAtividadeFisica(dto.atividadeFisica());
        }
        if (dto.motivoGratidao() != null) {
            r.setMotivoGratidao(dto.motivoGratidao());
        }

        RegistroDiario salvo = registroRepo.save(r);
        return toResponse(salvo);
    }

    public void deletar(Long pacienteId, Long registroId) {
        RegistroDiario r = registroRepo.findById(registroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        if (r.getPaciente() == null || !r.getPaciente().getId().equals(pacienteId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você só pode apagar seus próprios registros.");
        }

        registroRepo.delete(r);
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
                r.getMotivoGratidao());
    }
}
