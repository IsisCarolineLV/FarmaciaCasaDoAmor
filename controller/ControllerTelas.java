package controller;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
//Essa eh uma classe global para guardar um funcionario padrao pra a gente fazer testes
//e um controllerAcesso padrao pra facilitar o acesso as funcoes nos controllers das telas
import modelo.Funcionario;
import modelo.GeradorDeNotificacoes;
import modelo.Lote;

public class ControllerTelas {
  private static final ControllerAcesso acesso = new ControllerAcesso();;
  private static final Funcionario funcionarioPadrao = new Funcionario("admin", 123456789);
  private static boolean temNotificacoes = false;
  private static GeradorDeNotificacoes geradorDeNotificacoesVermelhas = new GeradorDeNotificacoes(true);
  private static GeradorDeNotificacoes geradorDeNotificacoesAmarelas = new GeradorDeNotificacoes(false);

  static {
    acesso.adicionarFuncionario(funcionarioPadrao);
    gerarNotificacoes();
  }

  public static ControllerAcesso getAcesso() {
    return acesso;
  }

  public static Funcionario getFuncionarioPadrao() {
    return funcionarioPadrao;
  }

  public static boolean temNotificacoes() {
    return temNotificacoes;
  }
  public static ArrayList<Pane> gerarNotificacoes(){
    ArrayList<Pane> panesNotificacoes = new ArrayList<>();
    ArrayList<Lote> lotesVermelhos = acesso.getVencidos();
    for(Lote l: lotesVermelhos){
      panesNotificacoes.add(geradorDeNotificacoesVermelhas.criarNotificacao("Lote Vencido:"+l.getMedicamento().getNome()));
    }
    ArrayList<Lote> lotesAmarelos = acesso.getQuaseVencidos();
    for(Lote l: lotesAmarelos){
      panesNotificacoes.add(geradorDeNotificacoesAmarelas.criarNotificacao("Lote Quase Vencido:"+l.getMedicamento().getNome()));
    }
    if(panesNotificacoes.size()>0)
      temNotificacoes = true;
    return panesNotificacoes;
  }
}
