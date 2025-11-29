package controller;
//Essa classe faz a mesma coisa que o controllerEstoque, 
//mas pedindo sempre os dados do funcionario responsavel
import java.sql.Date;
import java.util.ArrayList;
import modelo.*;

//kauan testou esse comentario pra ver como faz commit.
public class ControllerAcesso {
  private ArrayList<Estoque> estoque; 
  private ArrayList<Funcionario> funcionarios;
  private ArrayList<Medicamento> medicamentos;
  private Historico historico;

  public ControllerAcesso() {
    estoque = new ArrayList<>();
    funcionarios = new ArrayList<>();
    medicamentos = new ArrayList<>();
    historico = new Historico();
  }

  //////// Manipulacao da lista de medicamentos ////////////
  
  //agora precisa do funcionario
  /*public void adicionarMedicamento(Medicamento m) {
    if (pesquisarMedicamento(m) != null)
      medicamentos.add(m);
  }

  public void removerMedicamento(Medicamento m) throws Exception {
    if (pesquisarMedicamento(m) == null)
      throw new Exception("Esse medicamento nao existe no sistema!");
    medicamentos.remove(m);
    removerEstoque(getEstoque(m));
  }*/

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
      if (m.getCodigoDeBarras() == medicamento.getCodigoDeBarras()) {
        return m;
      }
    }
    return null;
  }

  public Medicamento pesquisarMedicamento(int codigoDeBarras) {
    for (Medicamento m : medicamentos) {
      if (m.getCodigoDeBarras() == codigoDeBarras) {
        return m;
      }
    }
    return null;
  }

  public void adicionarMedicamento(Funcionario f, String nome, int quantidadePorCartela,
      String composicao, int codigoDeBarras) throws Exception {
      if(pesquisarMedicamento(nome)!=null || pesquisarMedicamento(codigoDeBarras)!=null){
        System.out.println("Medicamento ja cadastrado");
        return;
      }
      if(pesquisarFuncionario(f) == null){
        throw new Exception("Esse funcionario nao existe no sistema!");
      }
      Medicamento novo = new Medicamento(nome, quantidadePorCartela, composicao, codigoDeBarras);
      medicamentos.add(novo);
      historico.adicionarAcesso(new AcessoMedicamento("Adicionou Medicamento", new Date(System.currentTimeMillis()), f, novo));
  }

  public void removerMedicamento(Funcionario f, Medicamento m) throws Exception {
    if(pesquisarFuncionario(f) == null){
        throw new Exception("Esse funcionario nao existe no sistema!");
    }

    if (pesquisarMedicamento(m) == null)
      throw new Exception("Esse medicamento nao existe no sistema!");

    medicamentos.remove(m);
    removerEstoque(getEstoque(m));  //detalhe importante pro bd, deve excluir os estoques antes do medicamento
    historico.adicionarAcesso(new AcessoMedicamento("Removeu Medicamento", new Date(System.currentTimeMillis()), f, m));
  }

  //////////// Manipulacao do estoque /////////////////////////////////
  public Estoque getEstoque(Medicamento m){
    for(Estoque e : estoque){
      if(e.getMedicamento().equals(m)){
        return e;
      }
    }
    return null;
  }

  public Estoque adicionarEstoque(Medicamento m){
    Estoque e = null;
    if(getEstoque(m)==null){
      e = new Estoque(m);
      estoque.add(e);
    }
    return e;
  }

  public void removerEstoque(Estoque e) throws Exception {
    if (getEstoque(e.getMedicamento()) == null)
      throw new Exception("Esse estoque nao existe no sistema!");
    estoque.remove(e);
  }

  public void removerEstoque(Medicamento m) throws Exception {
    Estoque e = getEstoque(m);
    if (e == null)
      throw new Exception("Esse estoque nao existe no sistema!");
    estoque.remove(e);
  }

  public void adicionarLoteEstoque(Lote l){
    Estoque e = getEstoque(l.getMedicamento());
    if (e == null){
      //se nao existe, cria o estoque
      e = adicionarEstoque(l.getMedicamento());
    }
    e.adicionarLote(l);
  }

  public boolean adicionarLoteEstoque(String nomeMedicamento, Date validade, int quantidadeComprimidos){
    Medicamento m = pesquisarMedicamento(nomeMedicamento);
    if(m==null){
      System.out.println("Medicamento nao cadastrado no sistema!");
      return false;
    }
    Estoque e = getEstoque(m);
    if (e == null){
      //se nao existe, cria o estoque
      System.out.println("Criou novo estoque para o medicamento "+nomeMedicamento);
      e = adicionarEstoque(m);
    } else if( e.pesquisarLote(validade) != null){
      //se ja existe o lote, atualiza a quantidade
      int novoTotal = e.pesquisarLote(validade).adicionarQuantidadeComprimidos(quantidadeComprimidos);
      System.out.println("Ja existe um lote com essa validade, atualizou a quantidade para "+novoTotal);
      return true;
    } 
    //cria o novo lote
    e.adicionarLote(new Lote(quantidadeComprimidos, validade, m));
    return true;
  }
  /*public Lote pesquisarLote(int idRemedio, Date validade) {
    ArrayList<Lote> lotesValidos = estoque.getLotes(idRemedio);
    for (Lote l : lotesValidos) {
      if (l.getValidade().equals(validade))
        return l;
    }
    return null;
  }*/

  public Lote pesquisarLote(String nomeMedicamento, Date validade) {
    ArrayList<Lote> lotesValidos = getEstoque(pesquisarMedicamento(nomeMedicamento)).getArrayLotes();
    for (Lote l : lotesValidos) {
      if (l.getValidade().equals(validade))
        return l;
    }
    return null;
  }

  //////////// Manipulacao da lista de Funcionarios /////////////////////

  public void adicionarFuncionario(Funcionario f) {
    if (pesquisarFuncionario(f) == null)
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
    Funcionario f = pesquisarFuncionario(CPFfuncionario);
    if (f == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Medicamento m = pesquisarMedicamento(nomeMedicamento);
    if (m == null)
      throw new Exception("Esse medicamneto nao existe no sistema!");

    String qual = atualiza(qualTipo, m, novoDado);
    historico.adicionarAcesso(new AcessoMedicamento(qual, new Date(System.currentTimeMillis()), f, m));
  }

  // sobrecarga pra pesquisar pelo id
  public <T> void atualizarMedicamento(long CPFfuncionario, int idRemedio, int qualTipo, T novoDado) throws Exception {
    Funcionario f = pesquisarFuncionario(CPFfuncionario);
    if ((f) == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Medicamento m = pesquisarMedicamento(idRemedio);
    if (m == null)
      throw new Exception("Esse medicamneto nao existe no sistema!");

    String qual = atualiza(qualTipo, m, novoDado);
    historico.adicionarAcesso(new AcessoMedicamento(qual, new Date(System.currentTimeMillis()), f, m));
  }

  private <T> String atualiza(int qualTipo, Medicamento m, T novoDado) throws Exception {
    switch (qualTipo) {
      case 1: { // atualizar nome do medicamento
        m.setNome((String) novoDado);
        return "Atualizou nome";
      }
      case 2: { // atualizar composicao do medicamento
        m.setComposicao((String) novoDado);
        return "Atualizou composicao";
      }
      case 3: { // atualizar quantos comprimidos vem na cartela
        m.setQuantidadePorCartela((int) novoDado);
        return "Atualizou quantidade por cartela";
      }
    }
    return "";
  }

  ///////////////// Atualizar dados dos lotes ////////////////////
  public <T> void atualizarLote(long CPFfuncionario, String nomeMedicamento, Date validade, int qualTipo, T novoDado)
      throws Exception {
    Funcionario f = pesquisarFuncionario(CPFfuncionario);
    if (f == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Lote l = pesquisarLote(nomeMedicamento, validade);
    if (l == null)
      throw new Exception("Esse lote nao existe no sistema!");

    String a = atualiza(qualTipo, l, novoDado);
    historico.adicionarAcesso(new Acesso(a, new Date(System.currentTimeMillis()), f, getEstoque(l.getMedicamento()), l));
  }

  // sobrecarga pra pesquisar pelo id
  public <T> void atualizarLote(long CPFfuncionario, int idRemedio, Date validade, int qualTipo, T novoDado)
      throws Exception {
    Funcionario f = pesquisarFuncionario(CPFfuncionario);
    if ( f == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Estoque e = getEstoque(pesquisarMedicamento(idRemedio));
    if (e == null)
      throw new Exception("Esse lote nao existe no sistema!");

    Lote l = e.pesquisarLote(validade); //pesquisa pela validade
    String a = atualiza(qualTipo, l, novoDado);
    historico.adicionarAcesso(new Acesso(a, new Date(System.currentTimeMillis()), f, getEstoque(l.getMedicamento()), l));
  }

  private <T> String atualiza(int qualTipo, Lote l, T novoDado) throws Exception {
    switch (qualTipo) {
      case 1: { // atualizar medicamento referente ao lote
        getEstoque(l.getMedicamento()).removerLote(l); //remove o lote do estoque antigo
        l.setMedicamento((Medicamento) novoDado); //atualiza o medicamento do lote
        adicionarLoteEstoque(l);  //adiciona o lote no estoque do novo medicamento
        return "Atualizou medicamento do lote";
      }
      case 2: { // atualizar quantos comprimidos tem no estoque
        l.setQuantidadeComprimidos((int) novoDado);
        return "Atualizou quantidade de comprimidos do lote";
      }
      case 3: { // atualizar data de validade do lote
        l.setValidade((Date) novoDado);
        return "Atualizou validade do lote";
      }
    }
    return "";
  }

  public void darBaixa(long CPFfuncionario, int idRemedio, Date validade) throws Exception {
    Funcionario f = pesquisarFuncionario(CPFfuncionario);
    if (f == null)
      throw new Exception("Esse funcionario nao existe no sistema!");

    Estoque e = getEstoque(pesquisarMedicamento(idRemedio));
    if (e == null)
      throw new Exception("Esse estoque nao existe no sistema!");

    Lote lote = e.pesquisarLote(validade);
    e.darBaixa(lote.getIdLote());
    historico.adicionarAcesso(new Acesso("Deu baixa", new Date(System.currentTimeMillis()), f, e, lote));
  }

  public ArrayList<Lote> getVencidos(){
    ArrayList<Lote> vencidos = new ArrayList<>();
    /*for(int i=0; i<3; i++){
      vencidos.add(new Lote(0, Date.valueOf("2023-01-01"), new Medicamento("Remedio Vencido", 10, "ComposicaoX", 123456789)));
    }*/
    for(Estoque e: estoque){
      vencidos.addAll(e.getVencidos());
    }
    return vencidos;
  }

  public ArrayList<Lote> getQuaseVencidos(){
    ArrayList<Lote> quaseVencidos = new ArrayList<>();
    /*for(int i=0; i<5; i++){
      quaseVencidos.add(new Lote(0, Date.valueOf("2023-12-31"), new Medicamento("Remedio Quase Vencido", 10, "ComposicaoY", 987654321)));
    }*/
    for(Estoque e: estoque){
      quaseVencidos.addAll(e.getQuaseVencidos());
    }
    return quaseVencidos;
  }

  public String imprimirHistorico(){
    return historico.toString();
  }

}
