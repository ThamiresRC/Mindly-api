package br.com.fiap.mindlyapi.controller;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import br.com.fiap.mindlyapi.service.PacienteProcedureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/db")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DbPacienteController {

    private final PacienteProcedureService pacienteProcedureService;

    @PostMapping("/pacientes/procedure")
    public ResponseEntity<?> inserirPacienteComProcedure(
            @Valid @RequestBody PacienteRequestDTO dto
    ) {
        Long idGerado = pacienteProcedureService.inserirPacienteViaProcedure(dto);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Paciente inserido via procedure no Oracle.",
                        "idGerado", idGerado,
                        "email", dto.email()
                )
        );
    }
}
