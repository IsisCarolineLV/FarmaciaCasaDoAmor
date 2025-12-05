package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Funcionario;

public class FuncionarioDAOJdbc implements FuncionarioDAO {

    private ConnectionFactory connectionFactory;

    public FuncionarioDAOJdbc() {
        this.connectionFactory = new ConnectionFactory();
    }

    @Override
    public void salvar(Funcionario funcionario) throws Exception {
         String sql = "INSERT INTO Funcionario(Nome,CPF) VALUES (?, ?)";

        try (Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCPF());
            stmt.execute();
        }
    }

    @Override
    public Funcionario getFuncionarioResponsavel(String cpf) throws Exception {
    String sql = "SELECT CPF, Nome "
               + "FROM Funcionario "
               + "WHERE CPF = ?";

    try (Connection con = connectionFactory.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, cpf);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String cpf_f = (rs.getString("CPF"));
                String nome = (rs.getString("Nome"));
                Funcionario f = new Funcionario(nome, cpf_f);
                return f;
            }
            }
        }
        // Se não achar, retorna null
        return null;
    }
  
}
