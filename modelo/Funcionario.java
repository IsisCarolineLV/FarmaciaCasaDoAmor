package modelo;

public class Funcionario {
  private String nome;
  private long CPF;

  public Funcionario(String nome, long cPF) {
    this.nome = nome;
    CPF = cPF;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public long getCPF() {
    return CPF;
  }

  public void setCPF(long cPF) {
    CPF = cPF;
  }

  public boolean equals(Funcionario f){
    if(f.getCPF()==CPF)
      return true;
    return false;
  }
}
