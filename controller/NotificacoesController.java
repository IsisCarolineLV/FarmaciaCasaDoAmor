package controller;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import model.Funcionario;
import model.GeradorDeNotificacoes;
import model.Lote;

public class NotificacoesController {
  private static final Funcionario funcionarioPadrao = new Funcionario("admin", 123456789);
  private static boolean temNotificacoes = false;
  private static GeradorDeNotificacoes geradorDeNotificacoesVermelhas = new GeradorDeNotificacoes(true);
  private static GeradorDeNotificacoes geradorDeNotificacoesAmarelas = new GeradorDeNotificacoes(false);

  static {
    //acesso.adicionarFuncionario(funcionarioPadrao); adicionar funcionario padrao no bd so uma vez
    gerarNotificacoes();
  }

  public static Funcionario getFuncionarioPadrao() {
    return funcionarioPadrao;
  }

  public static boolean temNotificacoes() {
    return temNotificacoes;
  }
  public static ArrayList<Pane> gerarNotificacoes(){
    /*
    ArrayList<Pane> panesNotificacoes = new ArrayList<>();
    ArrayList<Lote> lotesVermelhos = null;//acesso.getVencidos(); criar isso aqui no farmaciaService
    for(Lote l: lotesVermelhos){
      panesNotificacoes.add(geradorDeNotificacoesVermelhas.criarNotificacao("Lote Vencido:"+l.getMedicamento().getNome()));
    }
    ArrayList<Lote> lotesAmarelos = null;//acesso.getQuaseVencidos(); criar isso aqui no farmaciaService
    for(Lote l: lotesAmarelos){
      panesNotificacoes.add(geradorDeNotificacoesAmarelas.criarNotificacao("Lote Quase Vencido:"+l.getMedicamento().getNome()));
    }
    if(panesNotificacoes.size()>0)
      temNotificacoes = true;
    return panesNotificacoes;*/
    return null;
  }
}
