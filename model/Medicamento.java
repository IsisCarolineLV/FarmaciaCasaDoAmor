package model;

public class Medicamento {
  private String nome;
  private String composicao;
  private int quantidadePorCartela;
  //private Date validadeRemedio; //nao entendi pq tem validade no medicamento, mas ta ai
  private int codigoDeBarras;
  
  
  public Medicamento() {
  }

  public Medicamento (String nome, int quantidadePorCartela, String composicao,int codigoDeBarras){
    this.nome = nome;
    this.composicao = composicao;
    this.quantidadePorCartela = quantidadePorCartela;
    this.codigoDeBarras = codigoDeBarras;
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

  public int getCodigoDeBarras() {
    return codigoDeBarras;
  }

  public void setCodigoDeBarras(int codigoDeBarras) {
    this.codigoDeBarras = codigoDeBarras;
  }

  public String toString(){
    return "Codigo de barras:"+codigoDeBarras+
        "\nMedicamento: "+nome+
        "\nComposicao:"+composicao+
        "\nComprimidos por cartela:"+quantidadePorCartela;
  }
  
}
