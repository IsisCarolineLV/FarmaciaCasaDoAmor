package controller.dao;

import model.Funcionario;

public interface FuncionarioDAO {
  void salvar(Funcionario funcionario) throws Exception;
  Funcionario getFuncionarioResponsavel(String cpf) throws Exception;
}
