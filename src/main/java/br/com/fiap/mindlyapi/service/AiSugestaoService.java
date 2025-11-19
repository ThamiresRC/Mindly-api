package br.com.fiap.mindlyapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiSugestaoService {

    private final ChatModel chatModel;

    public String gerarSugestaoAutoCuidado(
            String moodDoDia,
            Integer nivelEstresse,
            String descricaoDia
    ) {
        StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("""
                Você é um assistente de saúde mental empático.
                Gere uma sugestão curta de autocuidado baseada nas informações abaixo.
                Texto em português do Brasil.
                Não mencione que é uma IA.
                """);

        promptBuilder.append("\n\nDados do paciente:\n");
        promptBuilder.append("• Humor: ").append(moodDoDia != null ? moodDoDia : "não informado").append("\n");
        promptBuilder.append("• Estresse: ").append(nivelEstresse != null ? nivelEstresse : -1).append("\n");
        promptBuilder.append("• Descrição: ").append(descricaoDia != null ? descricaoDia : "não informada").append("\n");

        UserMessage userMessage = new UserMessage(promptBuilder.toString());
        Prompt prompt = new Prompt(userMessage);

        var response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }
}