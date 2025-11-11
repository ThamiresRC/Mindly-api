-- PACIENTES
INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Ana Silva', 'ana@mindly.com', '123456', '11999990001','Ansiosa com o trabalho');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Bruno Souza', 'bruno@mindly.com', '123456', '11999990002', 'Dificuldade para dormir');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Carla Lima', 'carla@mindly.com', '123456', '11999990003', 'Crises de choro frequentes');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Diego Santos', 'diego@mindly.com', '123456', '11999990004','Relata muita pressão no trabalho');

INSERT INTO pacientes (nome, email, senha, telefone, observacao)
VALUES ('Fernanda Alves', 'fernanda@mindly.com', '123456', '11999990005', 'Dificuldade de foco');

-- PSICÓLOGOS
INSERT INTO psicologos (nome, email, senha)
VALUES ('Dra. Júlia Ferreira', 'julia@mindly.com', '123456');

INSERT INTO psicologos (nome, email, senha)
VALUES ('Dr. Marcos Pereira', 'marcos@mindly.com', '123456');

-- REGISTROS DIÁRIOS
INSERT INTO registros_diarios
(data, emocao, comeu_bem, dormiu_bem, relato_do_dia, desabafo, paciente_id)
VALUES
    (CURRENT_DATE, 'ANSIEDADE', TRUE, FALSE,
     'Dia puxado, muitas demandas no trabalho.',
     'Não aguento mais essa rotina, está me matando aos poucos.',
     1);

INSERT INTO registros_diarios
(data, emocao, comeu_bem, dormiu_bem, relato_do_dia, desabafo, paciente_id)
VALUES
    (CURRENT_DATE, 'TRISTE', FALSE, FALSE,
     'Briguei com o chefe hoje, me senti incapaz.',
     'Só queria sumir por um tempo.',
     2);

INSERT INTO registros_diarios
(data, emocao, comeu_bem, dormiu_bem, relato_do_dia, desabafo, paciente_id)
VALUES
    (CURRENT_DATE, 'FELIZ', TRUE, TRUE,
     'Recebi elogio do meu gestor hoje.',
     'Estou orgulhosa de mim.',
     3);

INSERT INTO registros_diarios
(data, emocao, comeu_bem, dormiu_bem, relato_do_dia, desabafo, paciente_id)
VALUES
    (CURRENT_DATE, 'RAIVA', TRUE, FALSE,
     'Muita pressão e pouca ajuda da equipe.',
     'Tenho vontade de largar tudo.',
     4);

INSERT INTO registros_diarios
(data, emocao, comeu_bem, dormiu_bem, relato_do_dia, desabafo, paciente_id)
VALUES
    (CURRENT_DATE, 'MEDO', FALSE, FALSE,
     'Preocupada com possível demissão.',
     'Tenho medo de não conseguir outro emprego.',
     5);
