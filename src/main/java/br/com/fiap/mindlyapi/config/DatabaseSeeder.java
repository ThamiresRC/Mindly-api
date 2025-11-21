package br.com.fiap.mindlyapi.config;

import br.com.fiap.mindlyapi.model.Paciente;
import br.com.fiap.mindlyapi.model.Psicologo;
import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final PacienteRepository pacienteRepository;
    private final PsicologoRepository psicologoRepository;

    @Override
    public void run(String... args) {
        seedPsicologoAdmin();
        seedPacientesDemo();
    }

    private void seedPsicologoAdmin() {
        if (!psicologoRepository.existsByEmail("admin@mindly.com")) {
            Psicologo admin = new Psicologo();
            admin.setNome("Admin Mindly");
            admin.setEmail("admin@mindly.com");
            admin.setSenha("123456"); //
            admin.setTelefone("11999990000");
            psicologoRepository.save(admin);
        }
    }

    private void seedPacientesDemo() {
        criarPacienteSeNaoExistir(
                "Ana Silva",
                "ana@mindly.com",
                "123456",
                "11999990001",
                "Ansiosa com o trabalho"
        );

        criarPacienteSeNaoExistir(
                "Bruno Souza",
                "bruno@mindly.com",
                "123456",
                "11999990002",
                "Dificuldade para dormir"
        );

        criarPacienteSeNaoExistir(
                "Carla Lima",
                "carla@mindly.com",
                "123456",
                "11999990003",
                "Crises de choro frequentes"
        );
    }

    private void criarPacienteSeNaoExistir(
            String nome,
            String email,
            String senha,
            String telefone,
            String observacao
    ) {
        if (!pacienteRepository.existsByEmail(email)) {
            Paciente p = new Paciente();
            p.setNome(nome);
            p.setEmail(email);
            p.setSenha(senha);
            p.setTelefone(telefone);
            p.setObservacao(observacao);
            pacienteRepository.save(p);
        }
    }
}
