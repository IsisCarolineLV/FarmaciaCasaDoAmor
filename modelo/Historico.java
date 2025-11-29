package modelo;

import java.util.ArrayList;

public class Historico {
  ArrayList<Acesso> acessos;

  public Historico() {
    acessos = new ArrayList<>();
  }

  public ArrayList<Acesso> getAcessos() {
    return acessos;
  }

  public void adicionarAcesso(Acesso novoAcesso) {
    acessos.add(novoAcesso);
  }

  public void limparHistorico() {
    acessos.clear();
  }

  public String toString() {
    String resultado = "Historico de Acessos:\n";
    for (Acesso a : acessos) {
      resultado += a.toString() + "\n";
    }
    return resultado;
  }

}
