package modelo;

import java.sql.Date;
import java.util.ArrayList;

public class Estoque {
  private ArrayList<Lote> lotes;

  public Estoque() {
    lotes = new ArrayList<>();
  }

  public ArrayList<Lote> getArrayLotes() {
    return lotes;
  }

  public void adicionarLote(Lote novoLote){
    //verifica se o lote ja nao foi inserido
    if(pesquisarLote(novoLote.getIdLote())==null)
      lotes.add(novoLote);
  }

  public void removerLote(Lote loteRemovido) throws Exception{

    if(pesquisarLote(loteRemovido.getIdLote())!=null){
      lotes.remove(loteRemovido);
    }

    throw new Exception("Esse lote nao existe no sistema!");
  }

  //retorna um arraylist com todos os lotes de um mesmo medicamento
  public ArrayList<Lote> getLotes(String nome){
    ArrayList<Lote> lotesComEsseMedicamento = new ArrayList<>();
    for(Lote l: lotes){

      if(l.getMedicamento().getNome().equals(nome)){
        lotesComEsseMedicamento.add(l);
      }

    }
    return lotesComEsseMedicamento;
  }

  //retorna um arraylist com todos os lotes de um mesmo medicamento
  public ArrayList<Lote> getLotes(int codigoDeBarras){
    ArrayList<Lote> lotesComEsseMedicamento = new ArrayList<>();
    for(Lote l: lotes){

      if(l.getMedicamento().getCodigoDeBarras()==codigoDeBarras){
        lotesComEsseMedicamento.add(l);
      }

    }
    return lotesComEsseMedicamento;
  }

  public Lote pesquisarLote(int idLote){
    for(Lote l: lotes){

      if(l.getIdLote()==idLote){
        return l;
      }

    }
    return null;
  }

  ////////////// ATUALIZAR AS INFORMACOES DOS LOTES: /////////////
  public void atualizar(int idLote, Date validade) throws Exception{
    Lote lote = pesquisarLote(idLote);
    if(lote==null) 
      throw new Exception("Lote "+idLote+" nao existe no sistema!");
    
    lote.setValidade(validade);
  }

  public void atualizar(int idLote, Medicamento medicamento) throws Exception{
    Lote lote = pesquisarLote(idLote);
    if(lote==null) 
      throw new Exception("Lote "+idLote+" nao existe no sistema!");
    
    lote.setMedicamento(medicamento);
  }

  public void darBaixa(int idLote) throws Exception{
    Lote lote = pesquisarLote(idLote);

    if(lote==null) 
      throw new Exception("Lote "+idLote+" nao existe no sistema!");

    if(lote.getQuantidadeComprimidos()<=0)
      throw new Exception("Estoque vazio!");
    
    lote.reduzirQuantidadeDeComprimidos();
  }
  
}
