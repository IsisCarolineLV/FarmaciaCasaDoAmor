package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Lote;
import modelo.Medicamento;

public class LoteDAOJdbc implements LoteDAO {

    private ConnectionFactory connectionFactory;

    public LoteDAOJdbc() {
        this.connectionFactory = new ConnectionFactory();
    }

    @Override
    public void salvar(Lote lote) throws Exception {
        String sql = "INSERT INTO Lote (Validade, QuantidadeComprimidos, IDRemedio, IDEstoque) VALUES (?, ?, ?, 1)";
        
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setDate(1, lote.getValidade());
            stmt.setInt(2, lote.getQuantidadeComprimidos());
            stmt.setInt(3, lote.getMedicamento().getCodigoDeBarras());
            
            stmt.execute();
        }
    }
    
    public List<Lote> listarTodos() throws Exception {
        String sql = "SELECT L.IDLote, L.Validade, L.QuantidadeComprimidos, L.IDRemedio, M.Nome " +
                     "FROM Lote L " +
                     "INNER JOIN Medicamento M ON L.IDRemedio = M.IDRemedio";

        List<Lote> lotes = new ArrayList<>();

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento med = new Medicamento();
                med.setCodigoDeBarras(rs.getInt("IDRemedio"));
                med.setNome(rs.getString("Nome"));

                Lote l = new Lote(
                    rs.getInt("QuantidadeComprimidos"),
                    rs.getDate("Validade"),
                    med
                );
                l.setIdLote(rs.getInt("IDLote"));
                
                lotes.add(l);
            }
        }
        return lotes;
    }

    @Override
    public List<Lote> listarPorMedicamento(int codigoBarras) throws Exception {
        String sql = "SELECT * FROM Lote WHERE IDRemedio = ?";
        List<Lote> lotes = new ArrayList<>();
        
        MedicamentoDAO medDao = new MedicamentoDAOJdbc();
        Medicamento med = medDao.buscarPorCodigoBarras(codigoBarras);

        if (med == null) return lotes;

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lote l = new Lote(
                    rs.getInt("QuantidadeComprimidos"),
                    rs.getDate("Validade"),
                    med
                );
                l.setIdLote(rs.getInt("IDLote"));
                lotes.add(l);
            }
        }
        return lotes;
    }
    
}
