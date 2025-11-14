package modelo;

import java.sql.Date;
import java.util.ArrayList;

public class Acesso {
  private Estoque estoque; // se forem ter mais de um aqui seria um arraylist
  private ArrayList<Funcionario> funcionarios;
  private ArrayList<Medicamento> medicamentos;

  public Acesso(Estoque estoque) {
    this.estoque = estoque;
    funcionarios = new ArrayList<>();
    medicamentos = new ArrayList<>();
  }

  //////// Manipulacao da lista de medicamentos ////////////
  public void adicionarMedicamento(Medicamento m) {
    if (pesquisarMedicamento(m) != null)
      medicamentos.add(m);
  }

  public void removerMedicamento(Medicamento m) throws Exception {
    if (pesquisarMedicamento(m) == null)
      throw new Exception("Esse medicamento nao existe no sistema!");
    medicamentos.remove(m);
  }

  public Medicamento pesquisarMedicamento(String nome) {
    for (Medicamento m : medicamentos) {
      if (m.getNome().equals(nome)) {
        return m;
      }
    }
    return null;
  }

  public Medicamento pesquisarMedicamento(Medicamento medicamento) {
    for (Medicamento m : medicamentos) {
      if (m.getIdRemedio() == medicamento.getIdRemedio()) {
        return m;
      }
    }
    return null;
  }

  public Medicamento pesquisarMedicamento(int idRemedio) {
    for (Medicamento m : medicamentos) {
      if (m.getIdRemedio() == idRemedio) {
        return m;
      }
    }
    return null;
  }

  //////////// Manipulacao da lista de Funcionarios /////////////////////

  public void adicionarFuncionario(Funcionario f) {
    if (pesquisarFuncionario(f) != null)
      funcionarios.add(f);
  }

  public void removerFuncionario(Funcionario f) throws Exception {
    if (pesquisarFuncionario(f) == null)
      throw new Exception("Esse funcionario nao existe no sistema!");
    funcionarios.remove(f);
  }

  public Funcionario pesquisarFuncionario(long CPF) {
    for (Funcionario f : funcionarios) {
      if (f.getCPF() == CPF) {
        return f;
      }
    }
    return null;
  }

  public Funcionario pesquisarFuncionario(Funcionario funcionario) {
    for (Funcionario f : funcionarios) {
      if (f.equals(funcionario)) {
        return f;
      }
    }
    return null;
  }

  ///////////////// Atualizar dados dos medicamentos ////////////////////

  public <T> void atualizarMedicamento(long CPFfuncionario, String nomeMedicamento, int qualTipo, T novoDado)
      throws Exception {
    if (pesquisarFuncionario(CPFfuncionario) == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Medicamento m = pesquisarMedicamento(nomeMedicamento);
    if (m == null)
      throw new Exception("Esse medicamneto nao existe no sistema!");

    atualiza(qualTipo, m, novoDado);
  }

  // sobrecarga pra pesquisar pelo id
  public <T> void atualizarMedicamento(long CPFfuncionario, int idRemedio, int qualTipo, T novoDado) throws Exception {
    if (pesquisarFuncionario(CPFfuncionario) == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Medicamento m = pesquisarMedicamento(idRemedio);
    if (m == null)
      throw new Exception("Esse medicamneto nao existe no sistema!");

    atualiza(qualTipo, m, novoDado);
  }

  private <T> void atualiza(int qualTipo, Medicamento m, T novoDado) {
    switch (qualTipo) {
      case 1: { // atualizar nome do medicamento
        m.setNome((String) novoDado);
        break;
      }
      case 2: { // atualizar composicao do medicamento
        m.setComposicao((String) novoDado);
        break;
      }
      case 3: { // atualizar quantos comprimidos vem na cartela
        m.setQuantidadePorCartela((int) novoDado);
        break;
      }
      case 4: { // atualizar data de validade
        m.setValidadeRemedio((Date) novoDado);
        break;
      }
    }
  }

}
