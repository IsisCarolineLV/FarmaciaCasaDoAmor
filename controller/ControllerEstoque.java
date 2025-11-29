package controller;
//Devido a nova mudanca essa classe nao e mais necessaria, mas caso queiramos
//manter a logica do estoque separada do acesso do funcionario, podemos usar ela
import java.sql.Date;
import java.util.ArrayList;

import modelo.*;

public class ControllerEstoque {
 /*private static Estoque estoque;
  private static ArrayList<Medicamento> medicamentos =  new ArrayList<>();

  public ControllerEstoque() {
  }

  public static void setEstoque( Estoque estoquezinho){
    estoque = estoquezinho;
  }

  //////// Manipulacao da lista de medicamentos ////////////
  public static void adicionarMedicamento(Medicamento m) {
    if (pesquisarMedicamento(m) != null)
      medicamentos.add(m);
  }

  public static void removerMedicamento(Medicamento m) throws Exception {
    if (pesquisarMedicamento(m) == null)
      throw new Exception("Esse medicamento nao existe no sistema!");
    medicamentos.remove(m);
  }

  public static Medicamento pesquisarMedicamento(String nome) {
    for (Medicamento m : medicamentos) {
      if (m.getNome().equals(nome)) {
        return m;
      }
    }
    return null;
  }

  public static Medicamento pesquisarMedicamento(Medicamento medicamento) {
    for (Medicamento m : medicamentos) {
      if (m.getCodigoDeBarras() == medicamento.getCodigoDeBarras()) {
        return m;
      }
    }
    return null;
  }

  public static Medicamento pesquisarMedicamento(int codigoDeBarras) {
    for (Medicamento m : medicamentos) {
      if (m.getCodigoDeBarras() == codigoDeBarras) {
        return m;
      }
    }
    return null;
  }

  public static void adicionarMedicamento(String nome, int quantidadePorCartela,
      String composicao, int codigoDeBarras) throws Exception {
      if(pesquisarMedicamento(nome)!=null || pesquisarMedicamento(codigoDeBarras)!=null){
        throw new Exception("Medicamento ja cadastrado");
      }
      Medicamento novo = new Medicamento(nome, quantidadePorCartela, composicao, codigoDeBarras);
      medicamentos.add(novo);
  }

  //////////// Manipulacao do estoque /////////////////////////////////
  public static void adicionarLote (String nomeMedicamento, int quantidadeComprimidos, Date validade){
    Medicamento m = pesquisarMedicamento(nomeMedicamento);
    if(m==null){
      //chamar tela de cadastro de medicamento
    }else{
      Lote novoLote = new Lote(quantidadeComprimidos, validade, m);
      estoque.adicionarLote(novoLote);
    }
  }

  public static Lote pesquisarLote(int idRemedio, Date validade) {
    ArrayList<Lote> lotesValidos = estoque.getLotes(idRemedio);
    for (Lote l : lotesValidos) {
      if (l.getValidade().equals(validade))
        return l;
    }
    return null;
  }

  public static Lote pesquisarLote(String nomeMedicamento, Date validade) {
    ArrayList<Lote> lotesValidos = estoque.getLotes(nomeMedicamento);
    for (Lote l : lotesValidos) {
      if (l.getValidade().equals(validade))
        return l;
    }
    return null;
  }

  public static <T> void atualizarMedicamento(String nomeMedicamento, int qualTipo, T novoDado)throws Exception {

    Medicamento m = pesquisarMedicamento(nomeMedicamento);
    if (m == null)
      throw new Exception("Esse medicamento nao existe no sistema!");

    atualiza(qualTipo, m, novoDado);
  }

  // sobrecarga pra pesquisar pelo id
  public static <T> void atualizarMedicamento(long CPFfuncionario, int idRemedio, int qualTipo, T novoDado) throws Exception {

    Medicamento m = pesquisarMedicamento(idRemedio);
    if (m == null)
      throw new Exception("Esse medicamneto nao existe no sistema!");

    atualiza(qualTipo, m, novoDado);
  }

  private static <T> void atualiza(int qualTipo, Medicamento m, T novoDado) throws Exception {
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
    }
  }

  ///////////////// Atualizar dados dos lotes ////////////////////

  public static <T> void atualizarLote(String nomeMedicamento, Date validade, int qualTipo, T novoDado)
      throws Exception {

    Lote l = pesquisarLote(nomeMedicamento, validade);
    if (l == null)
      throw new Exception("Esse lote nao existe no sistema!");

    atualiza(qualTipo, l, novoDado);
  }

  // sobrecarga pra pesquisar pelo id
  public static <T> void atualizarLote(int idRemedio, Date validade, int qualTipo, T novoDado)
      throws Exception {
    Lote l = pesquisarLote(idRemedio, validade);
    if (l == null)
      throw new Exception("Esse lote nao existe no sistema!");

    atualiza(qualTipo, l, novoDado);
  }

  private static <T> void atualiza(int qualTipo, Lote l, T novoDado) throws Exception {
    switch (qualTipo) {
      case 1: { // atualizar medicamento referente ao lote
        l.setMedicamento((Medicamento) novoDado);
        break;
      }
      case 2: { // atualizar quantos comprimidos tem no estoque
        l.setQuantidadeComprimidos((int) novoDado);
        break;
      }
      case 3: { // atualizar data de validade do lote
        l.setValidade((Date) novoDado);
        break;
      }
    }
  }

  public static void darBaixa(int idRemedio, Date validade) throws Exception {
    Lote lote = pesquisarLote(idRemedio, validade);
    if (lote == null)
      throw new Exception("Esse lote nao existe no sistema!");
    estoque.darBaixa(lote.getIdLote());
  }

  public static String imprimirLotes(){
    return estoque.toString();
  }*/

}
