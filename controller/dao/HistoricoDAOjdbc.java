package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    String sql = "SELECT CPF_Funcionario, Nome FROM Historico ORDER BY IDHistorico DESC LIMIT 1";

    try (Connection con = connectionFactory.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            String cpf = rs.getString("CPF_Funcionario");
            String nome = rs.getString("Nome");

            // Cria o objeto e seta os campos explicitamente (seguro independentemente do construtor)
            Funcionario f = new Funcionario(nome, cpf);

            return f;
        }
    } catch (Exception e) {
        e.printStackTrace();
        // opcional: lançar runtime exception ou tratar de outra forma
    }
    return null;
}
}
