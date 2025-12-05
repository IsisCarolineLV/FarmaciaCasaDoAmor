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
    public void registrarAcao(Long cpfFuncionario, String acao) throws Exception {
        String sql = "INSERT INTO Historico (CPF_Funcionario, Acao) VALUES (?, ?)";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1,123456789 );
            stmt.setString(2, acao);
            stmt.execute();
        }
    }
}
