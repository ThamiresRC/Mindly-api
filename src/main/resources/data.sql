-- ===================== PACIENTES =====================
INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Ana Silva', 'ana@mindly.com', '123456', '11999990001', 'Ansiosa com o trabalho');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Bruno Souza', 'bruno@mindly.com', '123456', '11999990002', 'Dificuldade para dormir');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Carla Lima', 'carla@mindly.com', '123456', '11999990003', 'Crises de choro frequentes');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Diego Santos', 'diego@mindly.com', '123456', '11999990004', 'Relata muita pressão no trabalho');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Fernanda Alves', 'fernanda@mindly.com', '123456', '11999990005', 'Dificuldade de foco');


INSERT INTO psicologos (nome, email, senha, telefone)
VALUES ('Dra. Júlia Ferreira', 'julia@mindly.com', '123456', '11988880001');

INSERT INTO psicologos (nome, email, senha, telefone)
VALUES ('Dr. Marcos Pereira', 'marcos@mindly.com', '123456', '11988880002');



INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
(
    CURRENT_DATE,
    'Dia puxado, muitas demandas no trabalho.',
    'ANSIEDADE',
    4,
    2,
    FALSE,
    'Conseguiu entregar tudo mesmo sob pressão.',
    1
);

INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
(
    CURRENT_DATE,
    'Briguei com o chefe hoje, me senti incapaz.',
    'TRISTE',
    4,
    2,
    FALSE,
    'Teve apoio de uma amiga para desabafar.',
    2
);

INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
(
    CURRENT_DATE,
    'Recebi elogio do meu gestor hoje.',
    'FELIZ',
    1,
    4,
    TRUE,
    'Reconhecimento pelo esforço no trabalho.',
    3
);

INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
(
    CURRENT_DATE,
    'Muita pressão e pouca ajuda da equipe.',
    'RAIVA',
    5,
    2,
    FALSE,
    'Mesmo irritado, conseguiu concluir as tarefas.',
    4
);

INSERT INTO registros_diarios
(data_registro, descricao_dia, mood_do_dia, nivel_estresse, qualidade_sono, atividade_fisica, motivo_gratidao, paciente_id)
VALUES
(
    CURRENT_DATE,
    'Preocupada com possível demissão.',
    'MEDO',
    4,
    3,
    FALSE,
    'Teve uma conversa sincera com o gestor.',
    5
);
