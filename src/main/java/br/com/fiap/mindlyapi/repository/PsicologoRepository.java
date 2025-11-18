package br.com.fiap.mindlyapi.repository;

import br.com.fiap.mindlyapi.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsicologoRepository extends JpaRepository<Psicologo, Long> {

    Optional<Psicologo> findByEmail(String email);

    boolean existsByEmail(String email);
}
