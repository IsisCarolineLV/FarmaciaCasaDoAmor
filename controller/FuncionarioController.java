package controller;

import model.Funcionario;
import model.dao.FuncionarioDAO;
import model.dao.FuncionarioDAOJdbc; 

public class FuncionarioController {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAOJdbc();
    }


    public boolean cadastrarFuncionario(String nome, String cpf) throws Exception {
        Funcionario novoFuncionario = new Funcionario(nome.toUpperCase(), cpf); 

        if (funcionarioJaCadastrado(cpf)) {
            return false;
        }

        funcionarioDAO.salvar(novoFuncionario);
        return true;
    }

    public boolean funcionarioJaCadastrado(String cpf) throws Exception {
        return funcionarioDAO.getFuncionarioResponsavel(cpf) != null;
    }

    public Funcionario validarLoginEBuscar(String nome, String cpf) throws Exception {
        Funcionario funcionario = funcionarioDAO.getFuncionarioResponsavel(cpf); 

        if (funcionario != null && funcionario.getNome().equalsIgnoreCase(nome.trim())) {
            return funcionario; // Retorna o objeto completo se a validação passar
        }
        return null; // Login falhou
    }
}