package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.dto.TokenResponseDTO;
import br.com.fiap.mindlyapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@Tag(
        name = "auth-controller",
        description = "Autenticação de usuários (paciente e psicólogo): registro e login com JWT."
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Registrar usuário e retornar token JWT",
            description = "Cria um novo usuário (paciente ou psicólogo) e retorna um token JWT já pronto para uso."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário registrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou e-mail já cadastrado."
            )
    })
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(
            @Valid @RequestBody PacienteRequestDTO dto
    ) {
        TokenResponseDTO response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Login e retorno do token JWT",
            description = "Autentica o usuário (paciente ou psicólogo) com e-mail e senha e devolve um token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas."
            )
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(
            @RequestBody Map<String, String> payload
    ) {
        String email = payload.get("email");
        String senha = payload.get("senha");

        TokenResponseDTO response = authService.login(email, senha);
        return ResponseEntity.ok(response);
    }
}
