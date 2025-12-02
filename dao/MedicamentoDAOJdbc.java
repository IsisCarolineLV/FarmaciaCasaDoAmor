package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Medicamento;

public class MedicamentoDAOJdbc implements MedicamentoDAO {

    private ConnectionFactory connectionFactory;

    public MedicamentoDAOJdbc() {
        this.connectionFactory = new ConnectionFactory();
    }

    @Override
    public void salvar(Medicamento m) throws Exception {
        String sql = "INSERT INTO Medicamento (IDRemedio, Nome, QuantidadeRemedio) VALUES (?, ?, ?)";
        
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, m.getCodigoDeBarras()); // IDRemedio
            stmt.setString(2, m.getNome());
            stmt.setInt(3, m.getQuantidadePorCartela()); // QuantidadeRemedio
            
            stmt.execute();
        }
    }

    @Override
    public Medicamento buscarPorCodigoBarras(int codigo) throws Exception {
        String sql = "SELECT * FROM Medicamento WHERE IDRemedio = ?";
        
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Medicamento(
                    rs.getString("Nome"),
                    rs.getInt("QuantidadeRemedio"),
                    "", // Composicao vazia pois não vem do banco
                    rs.getInt("IDRemedio")
                );
            }
        }
        return null;
    }
    
    @Override
    public Medicamento buscarPorNome(String nome) throws Exception{
        String sql = "SELECT * FROM Medicamento WHERE Nome = ?";

        try(Connection con = connectionFactory.getConnection(); 
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Medicamento(
                    rs.getString("Nome"),
                    rs.getInt("QuantidadeRemedio"),
                    "",
                    rs.getInt("IDRemedio")
                );
            }
        }
        return null;
    }

    @Override
    public List<Medicamento> listarTodos() throws Exception {
        String sql = "SELECT * FROM Medicamento";
        List<Medicamento> lista = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Medicamento(
                    rs.getString("Nome"),
                    rs.getInt("QuantidadeRemedio"),
                    "", 
                    rs.getInt("IDRemedio")
                ));
            }
        }
        return lista;
    }
}