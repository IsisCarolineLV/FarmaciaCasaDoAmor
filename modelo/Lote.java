package modelo;

import java.sql.Date;

public class Lote {
  private int idLote;  // ID LOTeeeeeee
  private int quantidadeComprimidos;
  private Date validade;
  private Medicamento medicamento;

  public Lote(int quantidadeCaixa, Date validade, Medicamento medicamento){
    quantidadeComprimidos=quantidadeCaixa*medicamento.getQuantidadePorCartela();
    this.validade= validade;
    this.medicamento=medicamento;
  }

  public int getIdLote() {
    return idLote;
  }

  /*fiz um setId porque quando for a hora de recriar os objetos a partir do banco 
  de dados pode ser que seja preciso ter esse metodo (se nao usarmos pode excluir)*/
  public void setIdLote(int idLote) {
    this.idLote = idLote;
  }

  public int getQuantidadeComprimidos() {
    return quantidadeComprimidos;
  }

  public void setQuantidadeComprimidos(int quantidadeComprimidos) {
    if(quantidadeComprimidos>0)
      this.quantidadeComprimidos = quantidadeComprimidos;
  }

  public int adicionarQuantidadeComprimidos(int quantidadeComprimidos) {
    if(quantidadeComprimidos>0)
      this.quantidadeComprimidos += quantidadeComprimidos;
    return this.quantidadeComprimidos;
  }

  public Date getValidade() {
    return validade;
  }

  public void setValidade(Date validade) {
    this.validade = validade;
  }

  public Medicamento getMedicamento() {
    return medicamento;
  }

  public void setMedicamento(Medicamento medicamento) {
    this.medicamento = medicamento;
  }

  public void reduzirQuantidadeDeComprimidos(){
    if(quantidadeComprimidos>0)
      quantidadeComprimidos--;
  }

  public String toString(){
    return "Lote nº"+idLote+
          "\n"+medicamento.toString()+
          "\nEstoque:"+quantidadeComprimidos+
          "\nValidade do lote:"+validade.toString();
  }
}
