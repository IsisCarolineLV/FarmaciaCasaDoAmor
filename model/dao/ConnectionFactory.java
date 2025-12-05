package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/Farmacia";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar. Verifique se seu SGBD está rodando, a senha e se Farmacia foi criado.", e);
        }
    }
}