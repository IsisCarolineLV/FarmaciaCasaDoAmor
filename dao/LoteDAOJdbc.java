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
        // Assume que existe um Estoque com ID 1 criado manualmente no banco
        String sql = "INSERT INTO Lote (IDLote, Validade, QuantidadeComprimidos, IDRemedio, IDEstoque) VALUES (?, ?, ?, ?, 1)";
        
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, lote.getIdLote());
            stmt.setDate(2, lote.getValidade());
            stmt.setInt(3, lote.getQuantidadeComprimidos());
            stmt.setInt(4, lote.getMedicamento().getCodigoDeBarras());
            
            stmt.execute();
        }
    }

    @Override
    public List<Lote> listarPorMedicamento(int codigoBarras) throws Exception {
        String sql = "SELECT * FROM Lote WHERE IDRemedio = ?";
        List<Lote> lotes = new ArrayList<>();
        
        // Busca o medicamento para preencher o objeto Lote
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