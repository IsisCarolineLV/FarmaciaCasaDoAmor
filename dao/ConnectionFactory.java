package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    public Connection getConnection() {
        Properties props = carregarPropriedades();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco de dados: " + e.getMessage());
        }
    }

    private Properties carregarPropriedades() {
        Properties props = new Properties();
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            props.load(fs);
        } catch (IOException e) {
            System.err.println("ERRO: O arquivo 'db.properties' não foi encontrado na raiz do projeto.");
            System.err.println("Crie o arquivo baseando-se no 'db.properties.example'.");
            throw new RuntimeException(e);
        }
        return props;
    }
}