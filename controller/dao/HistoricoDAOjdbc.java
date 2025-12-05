package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Funcionario;

public class HistoricoDAOjdbc implements HistoricoDAO {

    private ConnectionFactory connectionFactory;

    public HistoricoDAOjdbc() {
        this.connectionFactory = new ConnectionFactory();
    }

    @Override
    public void registrarAcao(String cpfFuncionario, String acao) throws Exception {
        String sql = "INSERT INTO Historico (CPF_Funcionario, Acao) VALUES (?, ?)";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1,cpfFuncionario);
            stmt.setString(2, acao);
            stmt.execute();
        }
    }

    public Funcionario buscarUltimoFuncionario() {
        String sql = "SELECT CPF_Funcionario FROM Historico ORDER BY IDHistorico DESC LIMIT 1";

        /*try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             rs = stmt.executeQuery()) {

            if (rs.next()) {
                String cpf = rs.getString("CPF_Funcionario");
                return new ("", cpf); // Nome vazio, apenas CPF
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null; // Retorna null se não encontrar
    }
}
