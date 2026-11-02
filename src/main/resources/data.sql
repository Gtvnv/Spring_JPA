-- Carga inicial de dados de exemplo (executada apos a criacao do schema).

-- Modalidades
INSERT INTO modalidade (nome, descricao) VALUES ('Musculacao', 'Treino de forca com pesos livres e maquinas');
INSERT INTO modalidade (nome, descricao) VALUES ('Spinning', 'Aula de ciclismo indoor de alta intensidade');
INSERT INTO modalidade (nome, descricao) VALUES ('Crossfit', 'Treinamento funcional de alta intensidade');
INSERT INTO modalidade (nome, descricao) VALUES ('Pilates', 'Exercicios de controle corporal e flexibilidade');

-- Planos
INSERT INTO plano (nome, valor_mensal, periodicidade) VALUES ('Mensal Basico', 99.90, 'MENSAL');
INSERT INTO plano (nome, valor_mensal, periodicidade) VALUES ('Trimestral', 89.90, 'TRIMESTRAL');
INSERT INTO plano (nome, valor_mensal, periodicidade) VALUES ('Anual Premium', 69.90, 'ANUAL');

-- Instrutores
INSERT INTO instrutor (nome, cref) VALUES ('Carlos Pereira', 'CREF-12345-G/SP');
INSERT INTO instrutor (nome, cref) VALUES ('Ana Souza', 'CREF-67890-G/SP');

-- Instrutor x Modalidade (ManyToMany)
INSERT INTO instrutor_modalidade (instrutor_id, modalidade_id) VALUES (1, 1); -- Carlos / Musculacao
INSERT INTO instrutor_modalidade (instrutor_id, modalidade_id) VALUES (1, 3); -- Carlos / Crossfit
INSERT INTO instrutor_modalidade (instrutor_id, modalidade_id) VALUES (2, 2); -- Ana / Spinning
INSERT INTO instrutor_modalidade (instrutor_id, modalidade_id) VALUES (2, 4); -- Ana / Pilates

-- Alunos (com endereco embutido)
INSERT INTO aluno (nome, email, cpf, data_nascimento, cep, logradouro, cidade, uf)
VALUES ('Joao da Silva', 'joao@email.com', '111.111.111-11', '1995-04-12', '01001-000', 'Praca da Se', 'Sao Paulo', 'SP');
INSERT INTO aluno (nome, email, cpf, data_nascimento, cep, logradouro, cidade, uf)
VALUES ('Maria Oliveira', 'maria@email.com', '222.222.222-22', '1990-09-30', '20040-002', 'Av. Rio Branco', 'Rio de Janeiro', 'RJ');

-- Matriculas
INSERT INTO matricula (aluno_id, plano_id, data_inicio, data_fim, status)
VALUES (1, 1, '2026-01-10', NULL, 'ATIVA');
INSERT INTO matricula (aluno_id, plano_id, data_inicio, data_fim, status)
VALUES (2, 3, '2026-02-01', NULL, 'ATIVA');
