package controller;

import controller.dao.FuncionarioDAO;
import controller.dao.FuncionarioDAOJdbc;
import model.Funcionario;

public class FuncionarioController {
  private FuncionarioDAO funcionarioDAO;

  public FuncionarioController() {
    this.funcionarioDAO = new FuncionarioDAOJdbc();
  }

  public boolean cadastrarFuncionario(String nome, String cpf) {
    try {
      Funcionario f = new Funcionario(nome, cpf);
      funcionarioDAO.salvar(f);
      return true;
    } catch (Exception e) {
      e.printStackTrace(); // Mostra erro no console se houver
      return false;
    }
  }

  public Funcionario buscarFuncionarioPorCPF(String cpf) {
    try {
      return funcionarioDAO.getFuncionarioResponsavel(cpf);
    } catch (Exception e) {
      e.printStackTrace(); // Mostra erro no console se houver
      return null;
    }
  }

  public boolean funcionarioJaCadastrado(String cpf) {
    try {
      Funcionario f = funcionarioDAO.getFuncionarioResponsavel(cpf);
      if (f != null) {
        return true; // Funcionário já cadastrado
      }
      return false; // Funcionário não cadastrado
    } catch (Exception e) {
      e.printStackTrace(); // Mostra erro no console se houver
      return false;
    }
  }
}
