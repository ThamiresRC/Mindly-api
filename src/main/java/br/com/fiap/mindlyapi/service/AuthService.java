package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.dto.PacienteResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PacienteRepository pacienteRepository;

    public PacienteResponseDTO register(PacienteRequestDTO dto) {
        if (pacienteRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe um paciente cadastrado com esse e-mail.");
        }

        Paciente paciente = Paciente.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha()) // sem criptografia por enquanto
                .telefone(dto.telefone())
                .build();

        Paciente saved = pacienteRepository.save(paciente);
        return new PacienteResponseDTO(
                saved.getId(),
                saved.getNome(),
                saved.getEmail(),
                saved.getTelefone(),
                saved.getObservacao()
        );
    }

    public PacienteResponseDTO login(String email, String senha) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

        if (!paciente.getSenha().equals(senha)) {
            throw new IllegalArgumentException("E-mail ou senha inválidos.");
        }

        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getObservacao()
        );
    }
}
