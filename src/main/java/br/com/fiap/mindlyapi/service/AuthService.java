package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.dto.TokenResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.Psicologo;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PacienteRepository pacienteRepository;
    private final PsicologoRepository psicologoRepository;
    private final TokenService tokenService;
    private final MessageSource messageSource;

    private String msg(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    public TokenResponseDTO register(PacienteRequestDTO dto) {

        boolean emailJaCadastrado =
                pacienteRepository.existsByEmail(dto.email())
                        || psicologoRepository.existsByEmail(dto.email());

        if (emailJaCadastrado) {
            throw new IllegalArgumentException(msg("paciente.email.ja.cadastrado"));
        }

        Paciente paciente = Paciente.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .telefone(dto.telefone())
                .build();

        Paciente saved = pacienteRepository.save(paciente);

        String token = tokenService.gerarToken(saved.getEmail(), "PACIENTE");

        return new TokenResponseDTO(
                saved.getNome(),
                saved.getEmail(),
                "PACIENTE",
                token
        );
    }

    public TokenResponseDTO login(String email, String senha) {

        Paciente paciente = pacienteRepository.findByEmail(email).orElse(null);
        if (paciente != null) {
            if (!paciente.getSenha().equals(senha)) {
                throw new IllegalArgumentException(msg("auth.credenciais.invalidas"));
            }

            String token = tokenService.gerarToken(paciente.getEmail(), "PACIENTE");
            return new TokenResponseDTO(
                    paciente.getNome(),
                    paciente.getEmail(),
                    "PACIENTE",
                    token
            );
        }

        Psicologo psicologo = psicologoRepository.findByEmail(email).orElse(null);
        if (psicologo != null) {
            if (!psicologo.getSenha().equals(senha)) {
                throw new IllegalArgumentException(msg("auth.credenciais.invalidas"));
            }

            String token = tokenService.gerarToken(psicologo.getEmail(), "PSICOLOGO");
            return new TokenResponseDTO(
                    psicologo.getNome(),
                    psicologo.getEmail(),
                    "PSICOLOGO",
                    token
            );
        }

        throw new IllegalArgumentException(msg("auth.credenciais.invalidas"));
    }
}
