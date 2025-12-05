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
import model.Lote;
import model.Medicamento;

public class EstoqueController {

    private MedicamentoDAO medicamentoDAO;
    private LoteDAO loteDAO;
    private HistoricoDAO historicoDAO;

    public EstoqueController() {
        // Instancia as implementações JDBC
        this.medicamentoDAO = new MedicamentoDAOJdbc();
        this.loteDAO = new LoteDAOJdbc();
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

    public boolean cadastrarLote(String nomeMed, int codigoBarras, Date validade, int qtd, Funcionario f) {
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
}