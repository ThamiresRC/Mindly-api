package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.IaSugestaoRequestDTO;
import br.com.fiap.mindlyapi.dto.IaSugestaoResponseDTO;
import br.com.fiap.mindlyapi.service.AiSugestaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "ia-controller",
        description = "Recursos de Inteligência Artificial Generativa com Spring AI para sugestões de autocuidado."
)
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IaController {

    private final AiSugestaoService aiSugestaoService;

    @Operation(
            summary = "Gerar sugestão de autocuidado com IA",
            description = "Gera uma sugestão de autocuidado baseada no humor, nível de estresse e descrição do dia do paciente, usando Spring AI."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sugestão gerada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IaSugestaoResponseDTO.class)
                    )
            )
    })
    @PostMapping("/sugestoes")
    public IaSugestaoResponseDTO gerarSugestao(@RequestBody IaSugestaoRequestDTO dto) {
        String sugestao = aiSugestaoService.gerarSugestaoAutoCuidado(
                dto.moodDoDia(),
                dto.nivelEstresse(),
                dto.descricaoDia()
        );

        return new IaSugestaoResponseDTO(sugestao);
    }
}
