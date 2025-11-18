# 🧠 Mindly — Global Solution (Java Advanced)

## 📘 Descrição do Projeto
O Mindly é uma aplicação completa desenvolvida em Java Spring Boot, com foco em saúde mental, autoavaliação e apoio psicológico.

A plataforma permite que pacientes registrem suas emoções, rotinas e bem-estar diariamente, enquanto psicólogos acompanham a evolução por meio de um painel profissional, com feedbacks, histórico e padrões emocionais.

A proposta busca oferecer uma solução acessível, moderna e multidisciplinar, integrando tecnologia com práticas de saúde mental — alinhada às necessidades reais de pacientes, incluindo pessoas com autismo, TDAH e transtornos de ansiedade..

---

## 🎯 Objetivos da Solução
- Auxiliar pacientes no **registro diário de emoções** e bem-estar.
- Facilitar o acompanhamento profissional por psicólogos.
- Identificar padrões emocionais e alertas críticos automaticamente.
- Tornar o cuidado psicológico mais acessível e contínuo.
- Fornecer uma solução moderna e escalável com **Spring Boot + JWT + PostgreSQL**.
- Integrar o Mindly ao app mobile (React Native).

---

## 🧠 Tecnologias Utilizadas
| Tecnologia                        | Uso |
|-----------------------------------|-----|
| **Java 17**                       | Linguagem principal |
| **Spring Boot 3.5.7**             | Framework de backend |
| **Spring Security + JWT (Auth0)** | Autenticação e autorização |
| **Spring Data JPA / Hibernate**   | Persistência |
| **H2 / Oracle**                   | Banco de dados local e acadêmico |
| **RabbitMQ (AMQP)**               | Mensageria e alertas assíncronos |
| **Swagger (Springdoc)**           | Documentação da API |
| **Lombok**                        | Redução de boilerplate |
| **React Native**                  | Aplicativo mobile |
| **Docker**                        | Containerização (opcional) |

---

## ⚙️ Funcionalidades Principais

### 👤 Perfil Paciente
- Registro diário com:
    - Emoção do dia
    - Nível de estresse
    - Qualidade do sono
    - Atividade física
    - Motivo de gratidão
- Histórico completo e filtrável.
- Feedback do psicólogo.
- Login com JWT.

### 🧑‍⚕️ Perfil Psicólogo
- Listagem e acompanhamento dos pacientes.
- Visualização completa dos registros.
- Envio de feedback para cada registro.
- Painel dedicado com estatísticas (em expansão).
- Acesso a **alertas críticos**.

### 🚨 Sistema de Alertas
Gera alerta automático quando:
- O humor está em estado crítico.
- O paciente usa palavras de risco (“triste”, “desesperado”, “ansiedade forte”, “quero sumir”…).
- Estresse muito elevado.

Alertas são enviados via **fila RabbitMQ** para processamento assíncrono.

---

## ☁️ Deploy e Ambientes
| Ambiente | URL | Observações |
|----------|-----|-------------|
| **Local** | http://localhost:8080/swagger-ui.html | Ambiente de desenvolvimento |
| **Produção (opcional)** | Render / Railway | Pode ser facilmente publicado |

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
 ├── application.properties
```

---

## 🧾 Endpoints Principais

### 🔐 Autenticação
- `POST /api/auth/login` — Login com JWT

### 📘 Registros diários
- `POST /api/registros` — Criar registro
- `GET /api/registros/paciente/{email}` — Listar registros por paciente
- `PUT /api/registros/{id}` — Atualizar
- `DELETE /api/registros/{id}` — Excluir
- `GET /api/registros/alertas` — Psicólogo vê alertas críticos

---

## 🧩 Integração Multidisciplinar
| Disciplina | Aplicação |
|-----------|-----------|
| **Java Advanced** | API completa com JWT, JPA, validação e mensageria |
| **Banco de Dados** | Scripts Oracle + H2, modelagem relacional |
| **Mobile** | App React Native consumindo a API |
| **DevOps** | Deploy possível via Render / Docker |
| **QA** | Testes via Swagger e Postman |

---

## 👩‍💻 Equipe
| Integrante | RM | Github |
|-------------|----|---------|
| **Thamires Ribeiro Cruz** | 558128 | [github.com/ThamiresRC](https://github.com/ThamiresRC) |
| **Adonay Rodrigues da Rocha** | 558782 | [github.com/AdonayRocha](https://github.com/AdonayRocha) |
| **Pedro Henrique Martins dos Reis** | 555306 | [github.com/pxxmdr](https://github.com/pxxmdr) |

---

## 📎 Repositório
➡️ https://github.com/ThamiresRC/mindly-api

---

## 📅 FIAP — 2º Ano | Java Advanced
Aplicação desenvolvida para estudos de **saúde mental assistida por tecnologia**.
