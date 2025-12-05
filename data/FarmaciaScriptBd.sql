-- Cria o banco de dados
CREATE DATABASE "Farmacia";

-- Conecta no banco recém-criado (Substituto do 'USE Farmacia;')
\c "Farmacia";

CREATE TABLE Funcionario (
    CPF VARCHAR(14) PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL
);

CREATE TABLE Estoque (
    IDEstoque INT PRIMARY KEY
);

-- Insere o estoque 1
INSERT INTO Estoque (IDEstoque) VALUES (1);

CREATE TABLE Medicamento (
    IDRemedio INT PRIMARY KEY, -- O codigo de barras
    Nome VARCHAR(100) NOT NULL,
    QuantidadeRemedio INT,
    ValidadeRemedio DATE  
);

CREATE TABLE Lote (
    IDLote SERIAL PRIMARY KEY, -- SERIAL substitui AUTO_INCREMENT
    Validade DATE NOT NULL,
    QuantidadeComprimidos INT NOT NULL,
    IDRemedio INT NOT NULL,
    IDEstoque INT NOT NULL,
    FOREIGN KEY (IDRemedio) REFERENCES Medicamento(IDRemedio),
    FOREIGN KEY (IDEstoque) REFERENCES Estoque(IDEstoque)
);

CREATE TABLE Acessa (
    IDEstoque INT,
    CPF_Funcionario VARCHAR(14),
    DataAcesso TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- TIMESTAMP substitui DATETIME
    PRIMARY KEY (IDEstoque, CPF_Funcionario),
    FOREIGN KEY (IDEstoque) REFERENCES Estoque(IDEstoque),
    FOREIGN KEY (CPF_Funcionario) REFERENCES Funcionario(CPF)
);