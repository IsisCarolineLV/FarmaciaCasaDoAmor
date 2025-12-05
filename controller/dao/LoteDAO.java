package controller.dao;

import java.util.ArrayList;

import model.Lote;

public interface LoteDAO {
    void salvar(Lote lote) throws Exception;

    ArrayList<Lote> listarPorMedicamento(int codigoBarrasMedicamento) throws Exception;
    ArrayList<Lote> listarQuaseVencidos() throws Exception;
    ArrayList<Lote> listarVencidos() throws Exception;
    void excluir(int idLote) throws Exception;
    void atualizarQuantidade(Lote lote) throws Exception;
}