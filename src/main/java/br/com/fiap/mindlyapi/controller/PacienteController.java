package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.FeedbackRequestDTO;
import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.dto.PacienteResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(
        name = "paciente-controller",
        description = "Gerenciamento de pacientes: cadastro, listagem, atualização, exclusão e feedback."
)
@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteRepository pacienteRepository;
    private final MessageSource messageSource;

    private String msg(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @Operation(
            summary = "Cadastrar paciente",
            description = "Cria um novo paciente a partir dos dados enviados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Paciente criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para criação de paciente.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponseDTO criar(@RequestBody @Valid PacienteRequestDTO dto) {
        Paciente entity = new Paciente();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setSenha(dto.senha());
        entity.setTelefone(dto.telefone());

        Paciente salvo = pacienteRepository.save(entity);
        return toResponse(salvo);
    }

    @Operation(
            summary = "Listar pacientes",
            description = "Retorna uma lista paginada de pacientes cadastrados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pacientes retornada com sucesso."
            )
    })
    @GetMapping
    public Page<PacienteResponseDTO> listarTodos(
            @Parameter(
                    description = "Parâmetros de paginação (page, size, sort). " +
                            "Exemplo: ?page=0&size=10&sort=nome,asc"
            )
            Pageable pageable
    ) {
        return pacienteRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Operation(
            summary = "Buscar paciente por ID",
            description = "Retorna os dados de um paciente específico pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public PacienteResponseDTO buscarPorId(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("paciente.nao.encontrado")
                ));
        return toResponse(paciente);
    }

    @Operation(
            summary = "Buscar paciente por e-mail",
            description = "Retorna os dados de um paciente a partir do seu e-mail."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/email/{email}")
    public PacienteResponseDTO buscarPorEmail(@PathVariable String email) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("paciente.nao.encontrado")
                ));
        return toResponse(paciente);
    }

    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza os dados de um paciente existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para atualização.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{id}")
    public PacienteResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PacienteRequestDTO dto
    ) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("paciente.nao.encontrado")
                ));

        paciente.setNome(dto.nome());
        paciente.setEmail(dto.email());
        paciente.setSenha(dto.senha());
        paciente.setTelefone(dto.telefone());

        Paciente salvo = pacienteRepository.save(paciente);
        return toResponse(salvo);
    }

    @Operation(
            summary = "Registrar feedback do paciente",
            description = "Atualiza o campo de observação (feedback) de um paciente identificado por e-mail."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback salvo com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/email/{email}/feedback")
    public PacienteResponseDTO salvarFeedback(
            @PathVariable String email,
            @RequestBody @Valid FeedbackRequestDTO dto
    ) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("paciente.nao.encontrado")
                ));

        paciente.setObservacao(dto.feedback());
        Paciente salvo = pacienteRepository.save(paciente);

        return toResponse(salvo);
    }

    @Operation(
            summary = "Excluir paciente",
            description = "Remove um paciente do sistema pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Paciente removido com sucesso. Sem conteúdo no corpo da resposta.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    msg("paciente.nao.encontrado")
            );
        }
        pacienteRepository.deleteById(id);
    }

    private PacienteResponseDTO toResponse(Paciente p) {
        return new PacienteResponseDTO(
                p.getId(),
                p.getNome(),
                p.getEmail(),
                p.getTelefone(),
                p.getObservacao()
        );
    }
}
