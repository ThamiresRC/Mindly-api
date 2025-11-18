package br.com.fiap.mindlyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Mindly API",
                version = "v1",
                description = "API do Mindly: gerenciamento de pacientes, psicólogos, registros diários e autenticação.",
                contact = @Contact(
                        name = "Mindly Team",
                        email = "mindly@fiap.com"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor local"
                )
        }
)
public class MindlyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindlyApiApplication.class, args);
    }
}
