package modelo;

import java.sql.Date;

//kauan testou esse comentario pra ver como faz commit.
public class Acesso {
  private String tipo;
  private Date dataDeAcesso;
  private Funcionario funcionario;
  private Estoque estoque;
  private Lote lote;

  public Acesso(String tipo, Date dataDeAcesso, Funcionario funcionario, Estoque estoque, Lote lote) {
    this.tipo = tipo;
    this.dataDeAcesso = dataDeAcesso;
    this.funcionario = funcionario;
    this.estoque = estoque;
    this.lote = lote;
  }

  public Acesso(String tipo, Date dataDeAcesso, Funcionario funcionario){
    this.tipo = tipo;
    this.dataDeAcesso = dataDeAcesso;
    this.funcionario = funcionario;
  }

  public String getTipo() {
    return tipo;
  }

  public Date getDataDeAcesso() {
    return dataDeAcesso;
  }

  public Funcionario getFuncionario() {
    return funcionario;
  }

  public String toString() {
    return "Acesso [tipo=" + tipo + ", dataDeAcesso=" + dataDeAcesso + ", funcionario=" + funcionario.getNome()
         + ", lote=" + lote.getMedicamento().getNome()+ ", vencimento="+lote.getValidade() + "]";
  }
}


