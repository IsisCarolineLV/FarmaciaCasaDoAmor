package model;

import java.util.ArrayList;

public class Historico {
  ArrayList<Acao> acessos;

  public Historico() {
    acessos = new ArrayList<>();
  }

  public ArrayList<Acao> getAcessos() {
    return acessos;
  }

  public void adicionarAcesso(Acao novoAcesso) {
    acessos.add(novoAcesso);
  }

  public void limparHistorico() {
    acessos.clear();
  }

  public String toString() {
    String resultado = "Historico de Acessos:\n";
    for (Acao a : acessos) {
      resultado += a.toString() + "\n";
    }
    return resultado;
  }

}
