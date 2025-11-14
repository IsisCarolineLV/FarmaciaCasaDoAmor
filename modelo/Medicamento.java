package modelo;

import java.sql.Date;

public class Medicamento {
  private int idRemedio;
  private String nome;
  private String composicao;
  private int quantidadePorCartela;
  private Date validadeRemedio; //nao entendi pq tem validade no medicamento, mas ta ai
  private int codigoDeBarras;
  private static int cont=0;

  public Medicamento (String nome, int quantidadePorCartela, Date validadeRemedio, String composicao,int codigoDeBarras){
    this.nome = nome;
    this.composicao = composicao;
    this.quantidadePorCartela = quantidadePorCartela;
    this.validadeRemedio = validadeRemedio;
    this.codigoDeBarras = codigoDeBarras;
    idRemedio = cont++;
  }

  public int getIdRemedio() {
    return idRemedio;
  }

  /*fiz um setId porque quando for a hora de recriar os objetos a partir do banco 
  de dados pode ser que seja preciso ter esse metodo (se nao usarmos pode excluir)*/
  public void setIdRemedio(int idRemedio) {
    this.idRemedio = idRemedio;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getComposicao() {
    return composicao;
  }

  public void setComposicao(String composicao) {
    this.composicao = composicao;
  }

  public int getQuantidadePorCartela() {
    return quantidadePorCartela;
  }

  public void setQuantidadePorCartela(int quantidadePorCartela) {
    this.quantidadePorCartela = quantidadePorCartela;
  }

  public Date getValidadeRemedio() {
    return validadeRemedio;
  }

  public void setValidadeRemedio(Date validadeRemedio) {
    this.validadeRemedio = validadeRemedio;
  }

  public int getCodigoDeBarras() {
    return codigoDeBarras;
  }

  public void setCodigoDeBarras(int codigoDeBarras) {
    this.codigoDeBarras = codigoDeBarras;
  }

  public String toString(){
    return "ID:"+idRemedio+
        "\nMedicamento: "+nome+
        "\nComposicao:"+composicao+
        "\nComprimidos por cartela:"+quantidadePorCartela+
        "\nValidade do remedio:"+validadeRemedio.toString();
  }
  
}