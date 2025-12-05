package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Lote;
import model.Medicamento;

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

    public ArrayList<Lote> listarTodos() throws Exception {
        String sql = "SELECT L.IDLote, L.Validade, L.QuantidadeComprimidos, L.IDRemedio, M.Nome " +
                "FROM Lote L " +
                "INNER JOIN Medicamento M ON L.IDRemedio = M.IDRemedio";

        ArrayList<Lote> lotes = new ArrayList<>();

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
                        med);
                l.setIdLote(rs.getInt("IDLote"));

                lotes.add(l);
            }
        }
        return lotes;
    }

    @Override
    public ArrayList<Lote> listarPorMedicamento(int codigoBarras) throws Exception {
        String sql = "SELECT * FROM Lote WHERE IDRemedio = ?";
        ArrayList<Lote> lotes = new ArrayList<>();

        MedicamentoDAO medDao = new MedicamentoDAOJdbc();
        Medicamento med = medDao.buscarPorCodigoBarras(codigoBarras);

        if (med == null)
            return lotes;

        try (Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, codigoBarras);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lote l = new Lote(
                        rs.getInt("QuantidadeComprimidos"),
                        rs.getDate("Validade"),
                        med);
                l.setIdLote(rs.getInt("IDLote"));
                lotes.add(l);
            }
        }
        return lotes;
    }

    @Override
    public ArrayList<Lote> listarVencidos() throws Exception {
        String sql =
            "select IDLote, QuantidadeComprimidos, Validade, IDRemedio, " +
            "       current_date - Validade as dias_vencidos " +
            "from Lote " +
            "where Validade < current_date";

        ArrayList<Lote> lotesVencidos = new ArrayList<>();
       MedicamentoDAO medicamentoDAO = new MedicamentoDAOJdbc();

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento med = medicamentoDAO.buscarPorID(rs.getInt("IDRemedio"));

                Lote l = new Lote(
                    rs.getInt("QuantidadeComprimidos"),
                    rs.getDate("Validade"),
                    med
                );
                l.setIdLote(rs.getInt("IDLote"));
                lotesVencidos.add(l);
            }
        }
        return lotesVencidos;
    }

    @Override
    public ArrayList<Lote> listarQuaseVencidos() throws Exception {
        String sql =
            "select IDLote, QuantidadeComprimidos, Validade, IDRemedio, " +
            "       current_date - Validade as dias_vencidos " +
            "from Lote " +
            "WHERE Validade BETWEEN CURRENT_DATE AND (CURRENT_DATE + INTERVAL '15 days');";

        ArrayList<Lote> lotesQuaseVencidos = new ArrayList<>();
       MedicamentoDAO medicamentoDAO = new MedicamentoDAOJdbc();

        try (Connection con = connectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Medicamento med = medicamentoDAO.buscarPorID(rs.getInt("IDRemedio"));

                Lote l = new Lote(
                    rs.getInt("QuantidadeComprimidos"),
                    rs.getDate("Validade"),
                    med
                );
                l.setIdLote(rs.getInt("IDLote"));
                lotesQuaseVencidos.add(l);
            }
        }
        return lotesQuaseVencidos;
    }

    public void atualizarQuantidade(Lote lote) throws Exception {
        String sql = "UPDATE Lote SET QuantidadeComprimidos = ? WHERE IDLote = ?";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, lote.getQuantidadeComprimidos());
            stmt.setInt(2, lote.getIdLote());

            stmt.executeUpdate();
        }
    }


    public void excluir(int idLote) throws Exception {
        String sql = "DELETE FROM Lote WHERE IDLote = ?";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idLote);

            stmt.executeUpdate();
        }
    }

    @Override
    public int buscarQuantidadeTotalPorMedicamento(int idMedicamento) throws Exception {
        // CORREÇÃO AQUI: Mudamos de "Quantidade" para "QuantidadeComprimidos"
        String sql = "SELECT SUM(QuantidadeComprimidos) as Total FROM Lote WHERE IDRemedio = ?";
        
        try (Connection con = connectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Total");
            }
        }
        return 0;
    }

    @Override
    public Lote buscarPorMedicamentoEValidade(Medicamento med, java.sql.Date validade) throws Exception {
        String sql = "SELECT * FROM Lote WHERE IDRemedio = ? AND Validade = ?";

        try (Connection con = connectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, med.getCodigoDeBarras());
            stmt.setDate(2, validade);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Lote lote = new Lote(
                    rs.getInt("QuantidadeComprimidos"),
                    rs.getDate("Validade"),
                    med
                );
                lote.setIdLote(rs.getInt("IDLote"));
                return lote;
            }
        }
        return null;
    }

}
