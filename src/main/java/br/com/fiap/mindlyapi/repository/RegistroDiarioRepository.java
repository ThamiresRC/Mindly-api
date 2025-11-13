package br.com.fiap.mindlyapi.repository;

import br.com.fiap.mindlyapi.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

    List<RegistroDiario> findByPacienteEmailOrderByDataRegistroDesc(String email);

    List<RegistroDiario> findByPacienteIdOrderByDataRegistroDesc(Long pacienteId);
}

