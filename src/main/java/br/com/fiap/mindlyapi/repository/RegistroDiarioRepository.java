package br.com.fiap.mindlyapi.repository;

import br.com.fiap.mindlyapi.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

    // lista de registros de um paciente específico, ordenados da data mais recente pra mais antiga
    List<RegistroDiario> findByPacienteIdOrderByDataDesc(Long pacienteId);
}
