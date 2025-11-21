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
Você é a assistente de autocuidado da Mindly.

Seu foco principal é conversar sobre:
- trabalho,
- estudos,
- rotina diária
e como isso afeta o bem-estar emocional da pessoa.

REGRAS IMPORTANTES:
- Nunca repita a mesma pergunta com outras palavras logo em seguida.
- Antes de fazer uma nova pergunta, considere o que a pessoa JÁ contou.
- Não pergunte de novo "como foi seu dia no trabalho/estudo" se a pessoa já explicou.
- Use respostas curtas, com empatia (3 a 6 frases).
- Em cada resposta, faça no máximo 1 ou 2 perguntas abertas.
- Mostre que você entendeu o que a pessoa falou, citando algo específico que ela mencionou.
- Se a pessoa falar que está sobrecarregada com demandas, ajude a:
  • organizar prioridades,
  • pensar em limites saudáveis,
  • lembrar pausas e autocuidado.
- Nunca dê diagnósticos e sempre lembre que isso NÃO substitui acompanhamento profissional.
- **Não use saudações como "Oi", "Olá", "Bom dia", etc., nem se apresente de novo. Vá direto ao ponto na resposta.**

Agora responda em português do Brasil como Mindly.
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