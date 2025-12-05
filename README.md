# 🏥 Farmácia Casa do Amor

Sistema de gerenciamento de estoque farmacêutico desenvolvido com o objetivo de apoiar a instituição filantrópica Casa do Amor. O sistema permite o controle de medicamentos, gerenciamento de lotes por validade, cadastro de funcionários e auditoria de ações.

## 📋 Funcionalidades

- **Controle de Acesso:** Login de funcionários via CPF.
- **Gerenciamento de Medicamentos:**
  - Cadastro de medicamentos com código de barras e composição.
  - Visualização de estoque total (agregado por lotes).
  - Remoção segura (remove lotes associados automaticamente).
- **Gerenciamento de Lotes:**
  - Cadastro de lotes com data de validade.
  - Controle de quantidade de caixas/comprimidos.
  - Baixa no estoque (consumo de comprimidos).
- **Notificações Inteligentes:**
  - Alerta de medicamentos vencidos (Vermelho).
  - Alerta de medicamentos próximos do vencimento (15 dias - Amarelo).
- **Histórico e Auditoria:**
  - Registro automático de ações (quem cadastrou o que e quando).

## 🛠️ Tecnologias Utilizadas

- **Linguagem:**           Java 8+
- **Interface Gráfica:**   JavaFX (FXML)
- **Banco de Dados:**      PostgreSQL
- **Persistência:**        JDBC (Padrão DAO)
- **IDE *recomendada*:**   VS Code ou Eclipse

## 🚀 Como Executar o Projeto

### 1. Pré-requisitos
Certifique-se de ter instalado:
- JDK (Java Development Kit) 1.8 ou superior.
- PostgreSQL.

Certifique-se de ter configurado:
- Driver JDBC do PostgreSQL (adicione-o ao Classpath ou Referenced Libraries da sua IDE).
- O arquivo `.jar` já está incluído na pasta libs do projeto.

### 2. Configuração do Banco de Dados
1. Crie um banco de dados no PostgreSQL chamado `Farmacia`.
2. Execute o script SQL localizado em `data/FarmaciaScriptBd.sql`.
3. Verifique o arquivo `modelo/dao/ConnectionFactory.java`. As credenciais padrão estão previamente configuradas.

### 3. Execução
Compile e execute o arquivo `Main.java` com o comando Run em sua IDE de preferência.
