package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.dto.PacienteResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.Psicologo;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PacienteRepository pacienteRepository;
    private final PsicologoRepository psicologoRepository;

    private static final String ADMIN_EMAIL = "admin@mindly.com";

    public PacienteResponseDTO register(PacienteRequestDTO dto) {

        boolean emailJaCadastrado =
                pacienteRepository.findByEmail(dto.email()).isPresent()
                        || psicologoRepository.findByEmail(dto.email()).isPresent();

        if (emailJaCadastrado) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail.");
        }

        if (ADMIN_EMAIL.equalsIgnoreCase(dto.email())) {
            Psicologo psicologo = Psicologo.builder()
                    .nome(dto.nome())
                    .email(dto.email())
                    .senha(dto.senha())
                    .telefone(dto.telefone())
                    .build();

            Psicologo saved = psicologoRepository.save(psicologo);

            return new PacienteResponseDTO(
                    saved.getId(),
                    saved.getNome(),
                    saved.getEmail(),
                    saved.getTelefone(),
                    null
            );
        }

        Paciente paciente = Paciente.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
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

        if (ADMIN_EMAIL.equalsIgnoreCase(email)) {
            Psicologo psicologo = psicologoRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

            if (!psicologo.getSenha().equals(senha)) {
                throw new IllegalArgumentException("E-mail ou senha inválidos.");
            }

            return new PacienteResponseDTO(
                    psicologo.getId(),
                    psicologo.getNome(),
                    psicologo.getEmail(),
                    psicologo.getTelefone(),
                    null
            );
        }

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
