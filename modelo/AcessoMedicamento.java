package modelo;

import java.sql.Date;

public class AcessoMedicamento extends Acesso {

  private Medicamento medicamento;

  public AcessoMedicamento(String tipo, Date dataDeAcesso, Funcionario funcionario, Medicamento medicamento) {
    super(tipo, dataDeAcesso, funcionario);
    this.medicamento = medicamento;
  }

  public String toString() {
    return "Acesso [tipo=" + super.getTipo() + ", dataDeAcesso=" + super.getDataDeAcesso() + ", funcionario=" + super.getFuncionario().getCPF()
        + ", medicamento=" + medicamento.getNome() + "]";
  }
  
}