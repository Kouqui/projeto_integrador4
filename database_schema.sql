-- =============================================
-- SCRIPT DE CRIAÇÃO DO BANCO DE DADOS FUT360
-- =============================================

-- Criar o banco de dados (se não existir)
CREATE DATABASE IF NOT EXISTS fut360;
USE fut360;

-- =============================================
-- TABELA: ATLETAS
-- =============================================
CREATE TABLE IF NOT EXISTS atletas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Ficha Técnica
    name VARCHAR(100) NOT NULL,
    fullname VARCHAR(200),
    position VARCHAR(50),
    category VARCHAR(50),
    photo VARCHAR(500),
    birthdate VARCHAR(20),
    age INT,
    nationality VARCHAR(50),
    height VARCHAR(20),
    weight VARCHAR(20),
    foot VARCHAR(20),

    -- Desempenho (Temporada)
    games INT DEFAULT 0,
    minutes INT DEFAULT 0,
    goals INT DEFAULT 0,
    assists INT DEFAULT 0,
    yellow_cards INT DEFAULT 0,
    red_cards INT DEFAULT 0,

    -- Carreira & Metas
    meta1 TEXT,
    meta2 TEXT,
    observation TEXT,

    -- Financeiro
    salary VARCHAR(50),
    contract_start VARCHAR(20),
    contract_end VARCHAR(20),
    release_clause VARCHAR(50),

    -- Análise do Último Treino
    treino_nota_geral DECIMAL(3,1) DEFAULT 0.0,
    treino_nota_tatica DECIMAL(3,1) DEFAULT 0.0,
    treino_nota_tecnica DECIMAL(3,1) DEFAULT 0.0,
    treino_nota_fisica DECIMAL(3,1) DEFAULT 0.0,
    treino_passes_certos INT DEFAULT 0,
    treino_passes_total INT DEFAULT 0,
    treino_chutes_certos INT DEFAULT 0,
    treino_chutes_total INT DEFAULT 0,
    treino_dribles_certos INT DEFAULT 0,
    treino_dribles_total INT DEFAULT 0,
    treino_desarmes INT DEFAULT 0,
    treino_interceptacoes INT DEFAULT 0,
    treino_km_percorridos DECIMAL(5,2) DEFAULT 0.0,
    treino_velocidade_max DECIMAL(5,2) DEFAULT 0.0,
    treino_sprints INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABELA: CALENDÁRIO
-- =============================================
CREATE TABLE IF NOT EXISTS calendario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    data_hora DATETIME NOT NULL,
    local VARCHAR(200),
    tipo VARCHAR(50), -- 'jogo', 'treino', 'reuniao', etc
    categoria VARCHAR(50), -- 'profissional', 'sub-17', 'sub-20', 'geral'

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABELA: TRANSAÇÕES FINANCEIRAS
-- =============================================
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL, -- 'receita' ou 'despesa'
    description VARCHAR(500) NOT NULL,
    value DECIMAL(15,2) NOT NULL,
    date DATE NOT NULL,
    category VARCHAR(100),
    responsible VARCHAR(200),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABELA: FOLHA SALARIAL
-- =============================================
CREATE TABLE IF NOT EXISTS payroll_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    role VARCHAR(100), -- Cargo/Função
    value DECIMAL(15,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pendente', -- 'pago' ou 'pendente'
    payment_date DATE NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABELA: CONTRATOS
-- =============================================
CREATE TABLE IF NOT EXISTS contracts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(200) NOT NULL,
    contract_type VARCHAR(50), -- 'profissional', 'formação', etc
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    salary DECIMAL(15,2),
    bonus DECIMAL(15,2),
    release_clause DECIMAL(15,2),
    status VARCHAR(20) DEFAULT 'ativo', -- 'ativo', 'rescindido', 'vencido'

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- INSERIR DADOS DE EXEMPLO
-- =============================================

-- Atletas de exemplo
INSERT INTO atletas (name, fullname, position, category, photo, birthdate, age, nationality, height, weight, foot,
                     games, minutes, goals, assists, yellow_cards, red_cards,
                     meta1, meta2, observation,
                     salary, contract_start, contract_end, release_clause,
                     treino_nota_geral, treino_nota_tatica, treino_nota_tecnica, treino_nota_fisica,
                     treino_passes_certos, treino_passes_total, treino_chutes_certos, treino_chutes_total,
                     treino_dribles_certos, treino_dribles_total, treino_desarmes, treino_interceptacoes,
                     treino_km_percorridos, treino_velocidade_max, treino_sprints)
VALUES
('Gabriel Reis', 'Gabriel Reis Silva', 'Atacante', 'Profissional', 'https://via.placeholder.com/40/9d4edd/FFFFFF?text=GR',
 '1995-03-15', 28, 'Brasil', '1.82m', '78kg', 'Direito',
 25, 2200, 15, 8, 3, 0,
 'Aumentar finalizações', 'Melhorar dribles', 'Jogador em alta',
 'R$ 50.000,00', '2023-01-01', '2026-12-31', 'R$ 500.000,00',
 10.0, 9.5, 9.8, 10.0,
 45, 50, 8, 10, 12, 15, 5, 3, 10.5, 32.5, 15),

('Carlos Eduardo', 'Carlos Eduardo Santos', 'Atacante', 'Profissional', 'https://via.placeholder.com/40/00aaff/FFFFFF?text=CE',
 '1996-07-22', 27, 'Brasil', '1.78m', '75kg', 'Esquerdo',
 23, 2050, 12, 10, 2, 0,
 'Melhorar finalizações', 'Aumentar assistências', 'Bom momento',
 'R$ 45.000,00', '2023-06-01', '2027-05-31', 'R$ 450.000,00',
 9.2, 9.0, 9.5, 9.5,
 42, 45, 7, 9, 10, 12, 4, 2, 10.2, 31.8, 14),

('Lucas Martins', 'Lucas Martins Oliveira', 'Zagueiro', 'Profissional', 'https://via.placeholder.com/40/33ff99/000000?text=LM',
 '1993-11-10', 30, 'Brasil', '1.88m', '85kg', 'Direito',
 26, 2340, 2, 1, 5, 1,
 'Manter regularidade', 'Melhorar saída de bola', 'Líder da defesa',
 'R$ 55.000,00', '2022-01-01', '2025-12-31', 'R$ 600.000,00',
 8.5, 8.8, 8.0, 9.2,
 50, 55, 2, 5, 3, 5, 15, 12, 9.8, 28.5, 10),

('Bruno Silva', 'Bruno Silva Costa', 'Meio-Campo', 'Profissional', 'https://via.placeholder.com/40/ffc107/000000?text=BS',
 '1997-05-18', 26, 'Brasil', '1.75m', '72kg', 'Direito',
 24, 2100, 5, 7, 4, 0,
 'Aumentar criatividade', 'Melhorar passes longos', 'Em evolução',
 'R$ 40.000,00', '2023-03-01', '2026-12-31', 'R$ 400.000,00',
 7.8, 8.0, 7.5, 8.0,
 48, 60, 3, 8, 8, 12, 8, 7, 10.0, 29.5, 12),

('Fernando Lima', 'Fernando Lima Souza', 'Lateral', 'Sub-20', 'https://via.placeholder.com/40/ff4d4d/FFFFFF?text=FL',
 '2004-02-25', 19, 'Brasil', '1.76m', '70kg', 'Esquerdo',
 15, 1200, 1, 3, 2, 0,
 'Ganhar mais experiência', 'Melhorar marcação', 'Jovem promissor',
 'R$ 8.000,00', '2024-01-01', '2026-12-31', 'R$ 50.000,00',
 4.8, 5.0, 5.5, 5.5,
 25, 45, 1, 5, 3, 8, 3, 2, 8.5, 26.0, 8);

-- Calendário de exemplo
INSERT INTO calendario (titulo, descricao, data_hora, local, tipo, categoria)
VALUES
('Jogo: Fut360 FC vs. Adversário FC', 'Campeonato Estadual - 19:00h - Estádio Principal',
 '2024-10-15 19:00:00', 'Estádio Principal', 'jogo', 'profissional'),

('Treino Físico - Sub-17', 'CT Secundário - 09:00h',
 '2024-10-16 09:00:00', 'CT Secundário', 'treino', 'sub-17'),

('Reunião de Planejamento', 'Sala da Diretoria - 15:00h',
 '2024-10-17 15:00:00', 'Sala da Diretoria', 'reuniao', 'geral');

-- Transações financeiras de exemplo
INSERT INTO transactions (type, description, value, date, category, responsible)
VALUES
('receita', 'Patrocínio Principal', 500000.00, '2024-10-01', 'Patrocínio', 'Diretoria'),
('receita', 'Venda de Ingressos', 150000.00, '2024-10-05', 'Bilheteria', 'Marketing'),
('receita', 'Direitos de TV', 800000.00, '2024-10-10', 'Direitos', 'Diretoria'),
('despesa', 'Manutenção do Estádio', 50000.00, '2024-10-03', 'Infraestrutura', 'Administração'),
('despesa', 'Compra de Equipamentos', 30000.00, '2024-10-07', 'Material', 'Departamento Esportivo'),
('despesa', 'Viagem para Jogo', 25000.00, '2024-10-12', 'Logística', 'Administração');

-- Folha salarial de exemplo
INSERT INTO payroll_items (name, role, value, status, payment_date)
VALUES
('Gabriel Reis', 'Atacante', 50000.00, 'pago', '2024-10-05'),
('Carlos Eduardo', 'Atacante', 45000.00, 'pago', '2024-10-05'),
('Lucas Martins', 'Zagueiro', 55000.00, 'pendente', '2024-11-05'),
('Bruno Silva', 'Meio-Campo', 40000.00, 'pendente', '2024-11-05'),
('Fernando Lima', 'Lateral', 8000.00, 'pendente', '2024-11-05'),
('João Técnico', 'Treinador Principal', 80000.00, 'pendente', '2024-11-05'),
('Maria Assistente', 'Preparadora Física', 35000.00, 'pendente', '2024-11-05');

-- Contratos de exemplo
INSERT INTO contracts (player_name, contract_type, start_date, end_date, salary, bonus, release_clause, status)
VALUES
('Gabriel Reis', 'profissional', '2023-01-01', '2026-12-31', 50000.00, 10000.00, 500000.00, 'ativo'),
('Carlos Eduardo', 'profissional', '2023-06-01', '2027-05-31', 45000.00, 8000.00, 450000.00, 'ativo'),
('Lucas Martins', 'profissional', '2022-01-01', '2025-12-31', 55000.00, 12000.00, 600000.00, 'ativo'),
('Bruno Silva', 'profissional', '2023-03-01', '2026-12-31', 40000.00, 7000.00, 400000.00, 'ativo'),
('Fernando Lima', 'formação', '2024-01-01', '2026-12-31', 8000.00, 2000.00, 50000.00, 'ativo');

-- =============================================
-- FIM DO SCRIPT
-- =============================================

SELECT 'Banco de dados FUT360 criado com sucesso!' AS Status;
