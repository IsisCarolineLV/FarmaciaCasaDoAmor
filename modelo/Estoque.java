package modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Estoque {
  private ArrayList<Lote> lotes;

  public Estoque() {
    lotes = new ArrayList<>();
  }

  public ArrayList<Lote> getArrayLotes() {
    return lotes;
  }

  public void adicionarLote(Lote novoLote) {
    // verifica se o lote ja nao foi inserido
    if (pesquisarLote(novoLote.getIdLote()) == null)
      lotes.add(novoLote);
  }

  public void removerLote(Lote loteRemovido) throws Exception {

    if (pesquisarLote(loteRemovido.getIdLote()) != null) {
      lotes.remove(loteRemovido);
    }

    throw new Exception("Esse lote nao existe no sistema!");
  }

  // retorna um arraylist com todos os lotes de um mesmo medicamento
  public ArrayList<Lote> getLotes(String nome) {
    ArrayList<Lote> lotesComEsseMedicamento = new ArrayList<>();
    for (Lote l : lotes) {

      if (l.getMedicamento().getNome().equals(nome)) {
        lotesComEsseMedicamento.add(l);
      }

    }
    return lotesComEsseMedicamento;
  }

  // retorna um arraylist com todos os lotes de um mesmo medicamento
  public ArrayList<Lote> getLotes(int codigoDeBarras) {
    ArrayList<Lote> lotesComEsseMedicamento = new ArrayList<>();
    for (Lote l : lotes) {

      if (l.getMedicamento().getCodigoDeBarras() == codigoDeBarras) {
        lotesComEsseMedicamento.add(l);
      }

    }
    return lotesComEsseMedicamento;
  }

  public Lote pesquisarLote(int idLote) {
    for (Lote l : lotes) {

      if (l.getIdLote() == idLote) {
        return l;
      }

    }
    return null;
  }

  public ArrayList<Lote> getVencidos() {
    //acho que da pra fazer isso com consulta no BD(??)
    Date hoje = Date.valueOf(LocalDate.now());
    ArrayList<Lote> lotesVencidos = new ArrayList<>();
    for (Lote l : lotes) {
      if (l.getValidade().before(hoje))
        lotesVencidos.add(l);
    }
    return lotesVencidos;
  }

  public ArrayList<Lote> getQuaseVencidos() {
    //acho que da pra fazer isso com consulta no BD(??)
    Date hoje = Date.valueOf(LocalDate.now());
    Date daqui2Semanas = Date.valueOf(LocalDate.now().plusWeeks(2));
    ArrayList<Lote> lotesQuaseVencidos = new ArrayList<>();
    for (Lote l : lotes) {
      if (l.getValidade().before(daqui2Semanas) && l.getValidade().after(hoje))
        lotesQuaseVencidos.add(l);
    }
    return lotesQuaseVencidos;
  }

  ////////////// ATUALIZAR AS INFORMACOES DOS LOTES: /////////////
  public void atualizar(int idLote, Date validade) throws Exception {
    Lote lote = pesquisarLote(idLote);
    if (lote == null)
      throw new Exception("Lote " + idLote + " nao existe no sistema!");

    lote.setValidade(validade);
  }

  public void atualizar(int idLote, Medicamento medicamento) throws Exception {
    Lote lote = pesquisarLote(idLote);
    if (lote == null)
      throw new Exception("Lote " + idLote + " nao existe no sistema!");

    lote.setMedicamento(medicamento);
  }

  public void darBaixa(int idLote) throws Exception {
    Lote lote = pesquisarLote(idLote);

    if (lote == null)
      throw new Exception("Lote " + idLote + " nao existe no sistema!");

    if (lote.getQuantidadeComprimidos() <= 0)
      throw new Exception("Estoque vazio!");

    lote.reduzirQuantidadeDeComprimidos();
    if (lote.getQuantidadeComprimidos() <= 0)
      removerLote(lote);
  }

  public String toString(){
    String a="Lista de lotes:\n";
    for(Lote l: lotes){
      a+=l.toString()+"\n\n";
    }
    return a;
  }

}
