# 🧠 Mindly — Global Solution (Java Advanced)

## 📘 Descrição do Projeto
O **Mindly** é uma plataforma completa de apoio psicológico, construída em **Java Spring Boot**, que integra pacientes e psicólogos por meio de registros diários, análise emocional e ferramentas de autocuidado assistidas por **Inteligência Artificial (Spring AI + Groq)**.

A aplicação permite que pacientes registrem emoções, hábitos e rotinas enquanto psicólogos acompanham a evolução e emitem feedbacks. A solução é pensada especialmente para torná‑la acessível, contínua e acolhedora — considerando também necessidades reais de pessoas com **autismo, TDAH, ansiedade e depressão**.

---

## 🎯 Objetivos da Solução
- Registrar e acompanhar o **bem-estar emocional diário**.
- Facilitar o acompanhamento psicológico profissional.
- Gerar **alertas críticos automáticos** com base em padrões emocionais.
- Fornecer sugestões de autocuidado usando **IA Generativa (Groq Llama 3.1)**.
- Integrar backend + mobile (React Native).
- Criar uma solução moderna, segura e escalável para o ecossistema FIAP.

---

## 🧠 Tecnologias Utilizadas
| Tecnologia | Uso |
|-----------|-----|
| **Java 17** | Linguagem principal |
| **Spring Boot 3.5.7** | Framework backend |
| **Spring Security + JWT (Auth0)** | Autenticação |
| **Spring Data JPA / Hibernate** | Persistência |
| **Oracle / H2** | Banco de dados |
| **RabbitMQ (AMQP)** | Mensageria |
| **Spring AI + Groq** | IA Generativa |
| **Swagger / Springdoc** | Documentação da API |
| **Lombok** | Redução de código repetitivo |
| **Docker** | Containerização |
| **React Native** | Aplicativo mobile |

---

## 🤖 IA Generativa Integrada (Spring AI + Groq)
A API conta com um módulo de Inteligência Artificial que:
- Gera **sugestões de autocuidado** para o paciente.
- Analisa texto emocional enviado no registro diário.
- Utiliza o modelo **Llama 3.1 8B Instant**, rápido e gratuito via Groq.

Endpoint:
```
POST /api/ia/sugestoes
```

---

## ⚙️ Funcionalidades Principais

### 👤 Paciente
- Registro diário contendo:
  - Emoção principal
  - Nível de estresse
  - Qualidade do sono
  - Atividade física
  - Motivo de gratidão
- Histórico completo.
- Feedback do psicólogo.
- Sugestões de autocuidado via IA.
- Login via JWT.

### 🧑‍⚕️ Psicólogo
- Lista e gerencia pacientes.
- Visualiza registros detalhados.
- Envia feedbacks.
- Recebe alertas críticos.
- Acompanha evolução emocional.

### 🚨 Sistema de Alertas
Dispara alerta quando:
- Emoção crítica é registrada.
- Palavras de risco são identificadas.
- Estresse muito elevado.

Alertas são enviados para filas **RabbitMQ**.

---

## ☁️ Deploy e Ambientes
| Ambiente | URL | Observações |
|----------|-----|-------------|
| **Local** | http://localhost:8080/swagger-ui.html | Desenvolvimento |
| **Produção (opcional)** | Render / Railway | Pode ser publicado facilmente |

---

## 📂 Estrutura do Projeto
```
mindly-api/
 ├── config/
 ├── controller/
 ├── dto/
 ├── model/
 ├── repository/
 ├── security/
 ├── service/
 ├── MessagingConfig.java
 ├── AlertMessagingService.java
 ├── AiSugestaoService.java
 ├── application.properties
```

---

## 🧾 Endpoints Principais

### 🔐 Autenticação
- POST `/api/auth/login`

### 📘 Registros Diários
- POST `/api/registros`
- GET `/api/registros/paciente/{email}`
- PUT `/api/registros/{id}`
- DELETE `/api/registros/{id}`
- GET `/api/registros/alertas`

### 🤖 Inteligência Artificial
- POST `/api/ia/sugestoes`

---

## 🧩 Integração Multidisciplinar
| Disciplina | Aplicação |
|-----------|-----------|
| **Java Advanced** | API completa com IA, JWT, JPA, mensageria |
| **Banco de Dados** | Modelagem + Oracle SQL |
| **Mobile** | App React Native consumindo a API |
| **DevOps** | Possível deploy com Docker e Render |
| **QA** | Testes via Swagger/Postman |

---

## 👩‍💻 Equipe
| Integrante | RM | Github |
|-----------|----|--------|
| **Thamires Ribeiro Cruz** | 558128 | https://github.com/ThamiresRC |
| **Adonay Rodrigues da Rocha** | 558782 | https://github.com/AdonayRocha |
| **Pedro Henrique Martins dos Reis** | 555306 | https://github.com/pxxmdr |

---

## 📎 Repositório
🔗 https://github.com/ThamiresRC/mindly-api

---

## 📅 FIAP — 2º Ano | Java Advanced
Projeto acadêmico baseado em **saúde mental, tecnologia e acessibilidade**, integrando várias disciplinas em uma única solução profissional.
