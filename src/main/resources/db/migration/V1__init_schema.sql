-- Flyway migration V1: initial schema + sample data (Portuguese)

-- Assessoria Corrida
CREATE TABLE assessoria_corrida (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255)
);

-- Treinador
CREATE TABLE treinador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    assessoria_corrida_id BIGINT,
    CONSTRAINT fk_treinador_assessoria FOREIGN KEY (assessoria_corrida_id) REFERENCES assessoria_corrida(id)
);

-- Nutricionista
CREATE TABLE nutricionista (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    assessoria_corrida_id BIGINT,
    CONSTRAINT fk_nutricionista_assessoria FOREIGN KEY (assessoria_corrida_id) REFERENCES assessoria_corrida(id)
);

-- Corredor
CREATE TABLE corredor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    assessoria_corrida_id BIGINT,
    CONSTRAINT fk_corredor_assessoria FOREIGN KEY (assessoria_corrida_id) REFERENCES assessoria_corrida(id)
);

-- Treino
CREATE TABLE treino (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    corredor_id BIGINT,
    treinador_id BIGINT,
    descricao VARCHAR(255),
    data DATE,
    concluido BOOLEAN,
    CONSTRAINT fk_treino_corredor FOREIGN KEY (corredor_id) REFERENCES corredor(id),
    CONSTRAINT fk_treino_treinador FOREIGN KEY (treinador_id) REFERENCES treinador(id)
);

-- Dieta
CREATE TABLE dieta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    corredor_id BIGINT,
    nutricionista_id BIGINT,
    descricao VARCHAR(255),
    data DATE,
    calorias INTEGER,
    proteinas FLOAT,
    carboidratos FLOAT,
    gorduras FLOAT,
    CONSTRAINT fk_dieta_corredor FOREIGN KEY (corredor_id) REFERENCES corredor(id),
    CONSTRAINT fk_dieta_nutricionista FOREIGN KEY (nutricionista_id) REFERENCES nutricionista(id)
);

-- Initial Seed Data (Optional, but good for testing if seed the button fails)
-- 1 Assessoria
INSERT INTO assessoria_corrida (nome) VALUES ('Assessoria UniRun');

-- 1 Treinador
INSERT INTO treinador (nome, assessoria_corrida_id) VALUES ('Treinador Rodrigo', 1);

-- 1 Nutricionista
INSERT INTO nutricionista (nome, assessoria_corrida_id) VALUES ('Nutri Bruno', 1);

-- 1 Corredor
INSERT INTO corredor (nome, assessoria_corrida_id) VALUES ('João Corredor', 1);

-- Treinos
INSERT INTO treino (corredor_id, treinador_id, descricao, data, concluido) VALUES (1, 1, 'Corrida Leve 5km', CURRENT_DATE, TRUE);
INSERT INTO treino (corredor_id, treinador_id, descricao, data, concluido) VALUES (1, 1, 'Tiro 10x400m', DATEADD('DAY', 2, CURRENT_DATE), FALSE);

-- Dietas
INSERT INTO dieta (corredor_id, nutricionista_id, descricao, data, calorias, proteinas, carboidratos, gorduras) VALUES (1, 1, 'Almoço Padrão', CURRENT_DATE, 600, 40.0, 60.0, 20.0);
