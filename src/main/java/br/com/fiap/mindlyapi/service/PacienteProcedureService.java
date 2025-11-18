package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.dto.PacienteRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PacienteProcedureService {

    private final JdbcTemplate jdbcTemplate;

    public Long inserirPacienteViaProcedure(PacienteRequestDTO dto) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRC_INSERIR_PACIENTE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_nome",        Types.VARCHAR),
                        new SqlParameter("p_email",       Types.VARCHAR),
                        new SqlParameter("p_senha",       Types.VARCHAR),
                        new SqlParameter("p_telefone",    Types.VARCHAR),
                        new SqlParameter("p_observacao",  Types.VARCHAR),
                        new SqlOutParameter("p_id_gerado", Types.NUMERIC)
                );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_nome", dto.nome())
                .addValue("p_email", dto.email())
                .addValue("p_senha", dto.senha())
                .addValue("p_telefone", dto.telefone())
                .addValue("p_observacao", "Cadastro via API Java / procedure");

        Map<String, Object> out = jdbcCall.execute(params);

        Number idGerado = (Number) out.get("p_id_gerado");
        if (idGerado == null) {
            throw new IllegalStateException("Procedure não retornou o ID do paciente.");
        }

        return idGerado.longValue();
    }
}
