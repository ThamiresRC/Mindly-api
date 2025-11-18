package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import br.com.fiap.mindlyapi.dto.PsicologoRequestDTO;
import br.com.fiap.mindlyapi.dto.PsicologoResponseDTO;
import br.com.fiap.mindlyapi.model.Psicologo;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(
        name = "psicologo-controller",
        description = "Gerenciamento de psicólogos: cadastro, listagem, consulta e alertas de registros críticos."
)
@RestController
@RequestMapping("/api/psicologos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PsicologoController {

    private final PsicologoRepository psicologoRepository;
    private final RegistroDiarioRepository registroDiarioRepository;
    private final MessageSource messageSource;

    private String msg(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @Operation(
            summary = "Cadastrar psicólogo",
            description = "Cria um novo psicólogo com nome, e-mail, senha e telefone."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Psicólogo criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PsicologoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para criação.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
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

    @Operation(
            summary = "Listar psicólogos",
            description = "Retorna todos os psicólogos cadastrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso."
    )
    @GetMapping
    public List<PsicologoResponseDTO> listar() {
        return psicologoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Operation(
            summary = "Buscar psicólogo por ID",
            description = "Retorna um psicólogo específico pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Psicólogo encontrado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PsicologoResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Psicólogo não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public PsicologoResponseDTO buscarPorId(
            @Parameter(description = "ID do psicólogo a ser buscado")
            @PathVariable Long id
    ) {
        Psicologo p = psicologoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("psicologo.nao.encontrado")
                ));
        return toResponse(p);
    }

    @Operation(
            summary = "Listar alertas de registros",
            description = "Lista todos os registros que contém palavras críticas como 'matar'."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de alertas retornada com sucesso."
    )
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
                        r.getDescricaoDia()
                ))
                .toList();
    }

    private PsicologoResponseDTO toResponse(Psicologo p) {
        return new PsicologoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getEmail()
        );
    }
}
