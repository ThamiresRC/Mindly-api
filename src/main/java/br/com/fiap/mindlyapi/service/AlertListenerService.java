package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.config.MessagingConfig;
import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlertListenerService {


    @RabbitListener(queues = MessagingConfig.ALERT_QUEUE)
    public void consumirAlerta(AlertaRegistroDTO alerta) {
        log.info(">>> [ALERTA CONSUMIDO] pacienteId={}, nome={}, telefone={}, mood={}, descricao={}",
                alerta.pacienteId(),
                alerta.pacienteNome(),
                alerta.telefone(),
                alerta.moodDia(),
                alerta.descricaoDia()
        );


    }
}
