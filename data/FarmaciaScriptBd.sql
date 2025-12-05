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

INSERT INTO Estoque (IDEstoque) VALUES (1)
ON CONFLICT (IDEstoque) DO NOTHING;

CREATE TABLE Medicamento (
    IDRemedio INT PRIMARY KEY,
	Composicao VARCHAR(100),
    Nome VARCHAR(100) NOT NULL,
    QuantidadeRemedio INT
);

CREATE TABLE Lote (
    IDLote SERIAL PRIMARY KEY,
    Validade DATE NOT NULL,
    QuantidadeComprimidos INT NOT NULL,
    IDRemedio INT NOT NULL,
    IDEstoque INT NOT NULL,
    FOREIGN KEY (IDRemedio) REFERENCES Medicamento(IDRemedio),
    FOREIGN KEY (IDEstoque) REFERENCES Estoque(IDEstoque)
);

CREATE TABLE Historico (
    IDHistorico SERIAL PRIMARY KEY,
    CPF_Funcionario VARCHAR(14) NOT NULL,
    DataDia DATE DEFAULT CURRENT_DATE,
    DataHora TIME DEFAULT CURRENT_TIME,
    Acao VARCHAR(200) NOT NULL,
    FOREIGN KEY (CPF_Funcionario) REFERENCES Funcionario(CPF)
);

INSERT INTO funcionario (Nome, CPF) VALUES ('ADMIN', '123456789');