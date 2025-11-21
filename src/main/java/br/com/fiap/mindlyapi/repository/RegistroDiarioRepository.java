package br.com.fiap.mindlyapi.repository;

import br.com.fiap.mindlyapi.model.RegistroDiario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

    Page<RegistroDiario> findByPacienteEmailOrderByDataRegistroDesc(String email, Pageable pageable);

    List<RegistroDiario> findByPacienteEmailOrderByDataRegistroDesc(String email);

    List<RegistroDiario> findByPacienteIdOrderByDataRegistroDesc(Long pacienteId);

    @Query("""
           select r
           from RegistroDiario r
           join r.paciente p
           where
               lower(r.descricaoDia) like '%morrer%'
               or lower(r.descricaoDia) like '%não aguento%'
               or lower(r.descricaoDia) like '%nao aguento%'
               or lower(r.descricaoDia) like '%me matar%'
               or lower(r.descricaoDia) like '%tirar minha vida%'
               or r.nivelEstresse >= 8
           order by r.dataRegistro desc
           """)
    List<RegistroDiario> findRegistrosEmAlerta();
}
