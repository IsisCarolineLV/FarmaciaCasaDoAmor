package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/Farmacia?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "admin";
    private static final String PASS = "1234";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar. Verifique se seu SGBD está rodando, a senha e se Farmacia foi criado.", e);
        }
    }
}