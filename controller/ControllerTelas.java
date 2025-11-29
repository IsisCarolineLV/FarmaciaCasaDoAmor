package controller;
//Essa eh uma classe global para guardar um funcionario padrao pra a gente fazer testes
//e um controllerAcesso padrao pra facilitar o acesso as funcoes nos controllers das telas
import modelo.Funcionario;

public class ControllerTelas {
  private static final ControllerAcesso acesso = new ControllerAcesso();;
  private static final Funcionario funcionarioPadrao = new Funcionario("admin", 123456789);;

  static {
    acesso.adicionarFuncionario(funcionarioPadrao);
  }

  public static ControllerAcesso getAcesso() {
    return acesso;
  }

  public static Funcionario getFuncionarioPadrao() {
    return funcionarioPadrao;
  }
}
