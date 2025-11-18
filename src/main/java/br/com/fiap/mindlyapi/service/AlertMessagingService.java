package br.com.fiap.mindlyapi.service;

import br.com.fiap.mindlyapi.config.MessagingConfig;
import br.com.fiap.mindlyapi.dto.AlertaRegistroDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertMessagingService {

    private final RabbitTemplate rabbitTemplate;

    public void enviarAlertaRegistro(AlertaRegistroDTO alerta) {
        try {
            rabbitTemplate.convertAndSend(MessagingConfig.ALERT_QUEUE, alerta);
            log.info("Alerta enviado para fila {}: pacienteId={}, mood={}, descricao={}",
                    MessagingConfig.ALERT_QUEUE,
                    alerta.pacienteId(),
                    alerta.moodDia(),
                    alerta.descricaoDia());
        } catch (AmqpConnectException ex) {
            log.warn("Não foi possível enviar alerta para a fila (broker indisponível): {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Erro ao enviar alerta para a fila: {}", ex.getMessage(), ex);
        }
    }
}
