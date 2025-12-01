package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void inicializar() {
        try {
            executarScriptSql();
            System.out.println("Base de dados verificada/inicializada com sucesso!");
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível executar o script de inicialização automaticamente.");
            System.err.println("Motivo: " + e.getMessage());
            System.err.println("Certifique-se de que o MySQL está em execução e a senha na ConnectionFactory está correta.");
        }
    }

    private static void executarScriptSql() throws SQLException, IOException {
        //Lê o ficheiro SQL da raiz do projeto
        Path caminhoSql = Paths.get("data", "FarmaciaScriptBd.sql");

        if (!Files.exists(caminhoSql)) {
            System.out.println("Ficheiro SQL não encontrado em /data. Ignorando a inicialização automática.");
            return;
        }

        String conteudoSql = new String(Files.readAllBytes(caminhoSql));
        
        String[] comandos = conteudoSql.split(";");

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection conn = factory.getConnection();
             Statement stmt = conn.createStatement()) {

            for (String comando : comandos) {
                if (comando.trim().isEmpty()) continue;
                
                try {
                    stmt.execute(comando);
                } catch (SQLException e) {}
            }
        }
    }
}