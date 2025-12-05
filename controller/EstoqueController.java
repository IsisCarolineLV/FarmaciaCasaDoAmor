package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import controller.dao.HistoricoDAO;
import controller.dao.LoteDAO;
import controller.dao.LoteDAOJdbc;
import controller.dao.MedicamentoDAO;
import controller.dao.MedicamentoDAOJdbc;
import model.Funcionario;
import model.Historico;
import model.Lote;
import model.Medicamento;
import controller.dao.HistoricoDAOjdbc;

public class EstoqueController {

    private MedicamentoDAO medicamentoDAO;
    private LoteDAO loteDAO;
    private HistoricoDAO historicoDAO;

    public EstoqueController() {
        // Instancia as implementações JDBC
        this.medicamentoDAO = new MedicamentoDAOJdbc();
        this.loteDAO = new LoteDAOJdbc();
        this.historicoDAO = new HistoricoDAOjdbc();
    }

    public boolean cadastrarMedicamento(String nome, int qtd, String comp, int cod, Funcionario f) {
        try {
            Medicamento m = new Medicamento(nome, qtd, comp, cod);
            medicamentoDAO.salvar(m);
            historicoDAO.registrarAcao(f.getCPF(), "Cadastrou medicamento "+ nome); //registra no historico a acao
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Mostra erro no console se houver
            return false;
        }
    }

    public boolean cadastrarLote(String nomeMed, Date validade, int qtd, Funcionario f) {
        try {
            Medicamento med = medicamentoDAO.buscarPorNome(nomeMed);

            if (med == null) {
                System.out.println("Medicamento" + nomeMed + "não encontrado.");
                return false;
            }
            
            Lote lote = new Lote(qtd, validade, med);
            loteDAO.salvar(lote);
            historicoDAO.registrarAcao(f.getCPF(), "Cadastrou lote de"+ nomeMed); //registra no historico a acao
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarFuncionario(String nomeMed, Date validade, int qtd, Funcionario f){
        try {
            Medicamento med = medicamentoDAO.buscarPorNome(nomeMed);

            if (med == null) {
                System.out.println("Medicamento" + nomeMed + "não encontrado.");
                return false;
            }
            
            Lote lote = new Lote(qtd, validade, med);
            loteDAO.salvar(lote);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Medicamento> buscarMedicamentos(String termo) {
        try {
            return medicamentoDAO.buscarPorNomeSemelhante(termo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String consumirDoLote(Lote lote, int qtdParaConsumir) {
        if (lote == null) return "Lote inválido.";

        if (qtdParaConsumir <= 0) {
            return "A quantidade deve ser maior que zero.";
        }

        if (qtdParaConsumir > lote.getQuantidadeComprimidos()) {
            return "Quantidade insuficiente (Disponível: " + lote.getQuantidadeComprimidos() + ").";
        }

        try {
            // Atualiza memória
            int novaQtd = lote.getQuantidadeComprimidos() - qtdParaConsumir;
            lote.setQuantidadeComprimidos(novaQtd);

            // Atualiza banco (Agora funciona pois adicionamos na Interface LoteDAO)
            loteDAO.atualizarQuantidade(lote); 

            return null; // Sucesso
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao atualizar: " + e.getMessage();
        }
    }


    public Medicamento buscarPorNome(String nome) {
        try {
            // Repassa a chamada para o DAO
            return medicamentoDAO.buscarPorNome(nome);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Medicamento buscarPorCodigoDeBarras(int codigoBarras) {
        try {
            // Repassa a chamada para o DAO
            return medicamentoDAO.buscarPorCodigoBarras(codigoBarras);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Historico> buscarHistoricoCompleto() {
        try {
            // Chama o novo método implementado no DAO
            return historicoDAO.buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}