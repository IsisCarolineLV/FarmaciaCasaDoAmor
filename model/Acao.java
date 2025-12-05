package model;

import java.time.LocalDate;
import java.time.LocalTime;

//registra a acao do funcionario para ir pro historico
public class Acao {
  
  private String tipo;
  private LocalDate dataDeAcao;
  private LocalTime horaDeAcao;
  private Funcionario funcionario;
  private Lote lote;

  public Acao(String tipo, Funcionario funcionario, Lote lote) {
    this.tipo = tipo;
    this.funcionario = funcionario;
    this.lote = lote;

    this.dataDeAcao = LocalDate.now();
    this.horaDeAcao = LocalTime.now();
  }

  public Acao(String tipo, Funcionario funcionario){
    this.tipo = tipo;
    this.funcionario = funcionario;

    this.dataDeAcao = LocalDate.now();
    this.horaDeAcao = LocalTime.now();
  }
}


