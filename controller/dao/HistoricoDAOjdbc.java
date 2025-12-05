package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        // CORREÇÃO: Usar JOIN para buscar o Nome da tabela Funcionario.
        // Assumindo que a coluna na tabela Funcionario é 'Nome' (com N maiúsculo)
        String sql = "SELECT h.CPF_Funcionario, f.Nome " + 
                     "FROM Historico h " +
                     "JOIN Funcionario f ON h.CPF_Funcionario = f.CPF " +
                     "ORDER BY h.IDHistorico DESC " + 
                     "LIMIT 1";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                // Aqui usamos f.Nome (ou apenas 'Nome' se o driver não se importar)
                String cpf = rs.getString("CPF_Funcionario");
                String nome = rs.getString("Nome"); 

                Funcionario f = new Funcionario(nome, cpf);

                return f;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // opcional: lanÃ§ar runtime exception ou tratar de outra forma
        }
        return null;
    }
}
