package controller.dao;

import java.util.List;

import model.Historico;

public interface HistoricoDAO {

    void registrarAcao(String cpfFuncionario, String acao) throws Exception;
    List<Historico> buscarTodos() throws Exception; // Assinatura corrigida
}
