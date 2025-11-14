-- ===================== PACIENTE (exemplo único) =====================
INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Paciente Exemplo', 'exemplo@mindly.com', '123456', '11900000000', 'Paciente cadastrado para testes.');

-- ===================== PSICÓLOGO (exemplo único) =====================
INSERT INTO psicologos (nome, email, senha, telefone)
VALUES ('Administrador', 'admin@mindly.com', '123456', '11900000000');

-- ===================== REGISTRO DIÁRIO (exemplo único) =====================
INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
    (
                CURRENT_DATE,
                'Dia exemplo criado automaticamente.',
                'FELIZ',
                1,
                5,
                TRUE,
                'Agradecido pelo aprendizado.',
                1
    );