package controller;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import model.Funcionario;
import model.GeradorDeNotificacoes;
import model.Lote;
import controller.dao.*;

public class NotificacoesController {
  private static Funcionario funcionarioResponsavel = new Funcionario("admin", "123.456.789-00");
  private static boolean temNotificacoes = false;
  //private ObservableList<Lote> masterData = FXCollections.observableArrayList();
  private static GeradorDeNotificacoes geradorDeNotificacoesVermelhas = new GeradorDeNotificacoes(true);
  private static GeradorDeNotificacoes geradorDeNotificacoesAmarelas = new GeradorDeNotificacoes(false);

  static {
    //acesso.adicionarFuncionario(funcionarioPadrao); adicionar funcionario padrao no bd so uma vez
    HistoricoDAOjdbc historicoDAOjdbc = new HistoricoDAOjdbc();
    gerarNotificacoes();
    funcionarioResponsavel = historicoDAOjdbc.buscarUltimoFuncionario();
    if(funcionarioResponsavel == null) {
        funcionarioResponsavel = new Funcionario("admin", "123456789");
    }
  }

  public static Funcionario getFuncionarioResponsavel() {
    return funcionarioResponsavel;
  }

  public static void setFuncionarioResponsavel(Funcionario f) {
    funcionarioResponsavel = f;
  }

  public static boolean temNotificacoes() {
    return temNotificacoes;
  }
  public static ArrayList<Pane> gerarNotificacoes(){
    ArrayList<Pane> panesNotificacoes = new ArrayList<>();
    ArrayList<Lote> lotesVermelhos = carregarLotesVencidosDoBanco();//null;//acesso.getVencidos(); criar isso aqui no farmaciaService
    if(lotesVermelhos!=null){
      for(Lote l: lotesVermelhos){
        panesNotificacoes.add(geradorDeNotificacoesVermelhas.criarNotificacao("Lote Vencido:"+l.getMedicamento().getNome()));
      }
    }
    
    ArrayList<Lote> lotesAmarelos = carregarLotesQuaseVencidosDoBanco();//carregarLotesDoBanco();//acesso.getQuaseVencidos(); criar isso aqui no farmaciaService
    if(lotesAmarelos!=null){
      for(Lote l: lotesAmarelos){
        panesNotificacoes.add(geradorDeNotificacoesAmarelas.criarNotificacao("Lote Quase Vencido:"+l.getMedicamento().getNome()));
      }
    }
    if(panesNotificacoes.size()>0)
      temNotificacoes = true;
    return panesNotificacoes;
  }

  private static ArrayList<Lote> carregarLotesVencidosDoBanco() {
      try {
          LoteDAOJdbc dao = new LoteDAOJdbc(); 
          ArrayList<Lote> lista = dao.listarVencidos();
          return lista;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }

  private static ArrayList<Lote> carregarLotesQuaseVencidosDoBanco() {
      try {
          LoteDAOJdbc dao = new LoteDAOJdbc(); 
          ArrayList<Lote> lista = dao.listarQuaseVencidos();
          return lista;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
}
