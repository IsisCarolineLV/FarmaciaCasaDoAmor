package model;
//sou a favor da gente tirar esse funcionario e o acesso
public class Funcionario {
  private String nome;
  private String CPF;

  public Funcionario(String nome, String cPF) {
    this.nome = nome;
    CPF = cPF;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCPF() {
    return CPF;
  }

  public void setCPF(String cPF) {
    CPF = cPF;
  }

  public boolean equals(Funcionario f){
    if(f.getCPF().equals(CPF))
      return true;
    return false;
  }
}
