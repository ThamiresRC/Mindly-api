package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioRequestDTO;
import br.com.fiap.mindlyapi.dto.RegistroDiarioResponseDTO;
import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.RegistroDiario;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.RegistroDiarioRepository;
import br.com.fiap.mindlyapi.service.AlertMessagingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Tag(
        name = "registro-diario-controller",
        description = "Gerenciamento de registros diários: criação, atualização, exclusão, listagem por paciente e alertas críticos."
)
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistroDiarioController {

    private final RegistroDiarioRepository registroRepo;
    private final PacienteRepository pacienteRepo;
    private final MessageSource messageSource;
    private final AlertMessagingService alertMessagingService;

    private String msg(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }



    @Operation(
            summary = "Criar registro diário",
            description = "Cria um novo registro diário associado a um paciente, identificado por e-mail."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistroDiarioResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para criação do registro.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroDiarioResponseDTO criar(
            @RequestBody @Valid RegistroDiarioRequestDTO dto
    ) {
        Paciente paciente = pacienteRepo.findByEmail(dto.emailPaciente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("paciente.nao.encontrado")
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

        enviarAlertaSeNecessario(salvo);

        return toResponse(salvo);
    }


    @Operation(
            summary = "Listar registros de um paciente",
            description = "Retorna uma lista paginada de registros diários de um paciente, ordenados por data decrescente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Registros retornados com sucesso."
    )
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO')")
    @GetMapping("/paciente/{email}")
    public Page<RegistroDiarioResponseDTO> listarPorPaciente(
            @Parameter(description = "E-mail do paciente para buscar os registros.")
            @PathVariable String email,
            @Parameter(
                    description = "Parâmetros de paginação (page, size, sort). " +
                            "Padrão: size=10, sort=dataRegistro,DESC"
            )
            @PageableDefault(size = 10, sort = "dataRegistro", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return registroRepo
                .findByPacienteEmailOrderByDataRegistroDesc(email, pageable)
                .map(this::toResponse);
    }


    @Operation(
            summary = "Atualizar registro diário",
            description = "Atualiza parcialmente os dados de um registro diário existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistroDiarioResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO')")
    @PutMapping("/{id}")
    public RegistroDiarioResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RegistroDiarioRequestDTO dto
    ) {
        RegistroDiario registro = registroRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        msg("registro.nao.encontrado")
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
        return toResponse(atualizado);
    }


    @Operation(
            summary = "Excluir registro diário",
            description = "Remove um registro diário pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Registro excluído com sucesso.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('PACIENTE','PSICOLOGO')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        if (!registroRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    msg("registro.nao.encontrado")
            );
        }
        registroRepo.deleteById(id);
    }



    @Operation(
            summary = "Listar alertas críticos",
            description = "Retorna alertas críticos consolidados por paciente, " +
                    "quando há palavras de risco na descrição ou nível de estresse elevado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Alertas retornados com sucesso."
    )
    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/alertas")
    public List<AlertaRegistroDTO> listarAlertasCriticos() {
        return registroRepo.findAll().stream()
                .filter(this::isRegistroCritico)
                .map(r -> {
                    var paciente = r.getPaciente();
                    return new AlertaRegistroDTO(
                            paciente.getId(),
                            paciente.getNome(),
                            paciente.getTelefone(),
                            r.getMoodDoDia(),
                            r.getDescricaoDia()
                    );
                })
                .toList();
    }


    private boolean isRegistroCritico(RegistroDiario r) {
        String texto = (r.getDescricaoDia() == null ? "" : r.getDescricaoDia()).toLowerCase();
        Integer nivelEstresse = r.getNivelEstresse();

        boolean textoCritico =
                texto.contains("tirar a minha vida") ||
                        texto.contains("tirar minha vida") ||
                        texto.contains("acabar com a minha vida") ||
                        texto.contains("me matar") ||
                        texto.contains("não aguento mais") ||
                        texto.contains("nao aguento mais") ||
                        texto.contains("suicid") ||
                        texto.contains("morrer");

        boolean estresseAlto = (nivelEstresse != null && nivelEstresse >= 4);

        return textoCritico || estresseAlto;
    }

    private void enviarAlertaSeNecessario(RegistroDiario r) {
        if (!isRegistroCritico(r)) {
            return;
        }

        var paciente = r.getPaciente();

        AlertaRegistroDTO alerta = new AlertaRegistroDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getTelefone(),
                r.getMoodDoDia(),
                r.getDescricaoDia()
        );

        alertMessagingService.enviarAlertaRegistro(alerta);
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
