package dao;

import java.util.List;
import modelo.Medicamento;

public interface MedicamentoDAO {
    void salvar(Medicamento m) throws Exception;
    Medicamento buscarPorCodigoBarras(int codigo) throws Exception;
    Medicamento buscarPorNome(String nome) throws Exception;
    List<Medicamento> buscarPorNomeSemelhante(String termo) throws Exception;
    List<Medicamento> listarTodos() throws Exception;
}