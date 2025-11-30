CREATE DATABASE if not exists Farmacia;

USE Farmacia;

CREATE TABLE Funcionario (
    CPF VARCHAR(14) PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL
);

CREATE TABLE Estoque (
    IDEstoque INT PRIMARY KEY
);

CREATE TABLE Medicamento (
    IDRemedio INT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    QuantidadeRemedio INT,
    ValidadeRemedio DATE  
);

CREATE TABLE Lote (
    IDLote INT PRIMARY KEY,
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
    DataAcesso DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (IDEstoque, CPF_Funcionario),
    FOREIGN KEY (IDEstoque) REFERENCES Estoque(IDEstoque),
    FOREIGN KEY (CPF_Funcionario) REFERENCES Funcionario(CPF)
);