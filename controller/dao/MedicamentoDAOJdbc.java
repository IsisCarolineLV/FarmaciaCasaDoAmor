package controller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Medicamento;

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
            
            stmt.setInt(1, m.getCodigoDeBarras()); 
            stmt.setString(2, m.getNome());
            stmt.setInt(3, m.getQuantidadePorCartela());
            
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
                    "", 
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

    @Override
    public List<Medicamento> buscarPorNomeSemelhante(String termo) throws Exception {
        String sql = "SELECT * FROM Medicamento WHERE Nome LIKE ?";
        List<Medicamento> lista = new ArrayList<>();
        
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + termo + "%"); 
            ResultSet rs = stmt.executeQuery();

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
    
    public void atualizarQuantidade(int idRemedio, int novaQuantidade) throws Exception {
        String sql = "UPDATE Medicamento SET QuantidadeRemedio = ? WHERE IDRemedio = ?";
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idRemedio);
            stmt.execute();
        }
    }

    public void remover(int idRemedio) throws Exception {
        String sqlLote = "DELETE FROM Lote WHERE IDRemedio = ?";
        
        String sqlMedicamento = "DELETE FROM Medicamento WHERE IDRemedio = ?";

        try (Connection con = connectionFactory.getConnection()) {
            
            try (PreparedStatement stmtLote = con.prepareStatement(sqlLote)) {
                stmtLote.setInt(1, idRemedio);
                stmtLote.execute();
            } 

            try (PreparedStatement stmtMed = con.prepareStatement(sqlMedicamento)) {
                stmtMed.setInt(1, idRemedio);
                stmtMed.execute();
            } 
        }
    }

    @Override
    public Medicamento buscarPorID(int id) throws Exception {
    String sql = "SELECT IDRemedio, Nome, Composicao, QuantidadeRemedio "
               + "FROM Medicamento "
               + "WHERE IDRemedio = ?";

    try (Connection con = connectionFactory.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int idRemedio = (rs.getInt("IDRemedio"));
                String nome = (rs.getString("Nome"));
                String composicao = (rs.getString("Composicao"));
                int quantidade = (rs.getInt("QuantidadeRemedio"));
                Medicamento m = new Medicamento(nome, quantidade,composicao, idRemedio);
                return m;
            }
            }
        }
        // Se não achar, retorna null
        return null;
    }
}
