package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Funcionario;
import model.Historico;

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

    @Override // Garante que a assinatura está sendo verificada
    public List<Historico> buscarTodos() throws Exception { // Assinatura corrigida
        List<Historico> historicos = new ArrayList<>();

        // Query com JOIN para obter o Nome do Funcionario e ordenar do mais recente ao mais antigo
        String sql = "SELECT h.IDHistorico, h.CPF_Funcionario, f.Nome, h.Acao, h.DataDia, h.DataHora " +
                     "FROM Historico h " +
                     "JOIN Funcionario f ON h.CPF_Funcionario = f.CPF " +
                     "ORDER BY h.IDHistorico DESC";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Historico h = new Historico(
                    rs.getInt("IDHistorico"),
                    rs.getString("CPF_Funcionario"),
                    rs.getString("Nome"), // Nome do Funcionario
                    rs.getString("Acao"),
                    rs.getDate("DataDia"),
                    rs.getTime("DataHora")
                );
                historicos.add(h);
            }
        } 

        return historicos;
    }

public Funcionario buscarUltimoFuncionario() {
        String sql = "SELECT h.CPF_Funcionario, f.Nome " + 
                     "FROM Historico h " +
                     "JOIN Funcionario f ON h.CPF_Funcionario = f.CPF " +
                     "ORDER BY h.IDHistorico DESC " + 
                     "LIMIT 1";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String cpf = rs.getString("CPF_Funcionario");
                String nome = rs.getString("Nome"); 

                Funcionario f = new Funcionario(nome, cpf);

                return f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
