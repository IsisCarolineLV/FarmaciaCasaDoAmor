package dao;

import java.util.List;
import modelo.Lote;

public interface LoteDAO {
    void salvar(Lote lote) throws Exception;
    List<Lote> listarPorMedicamento(int codigoBarrasMedicamento) throws Exception;
}