package controller;

import java.io.IOException;
import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaCadastroMedicamentoController{

    @FXML
    private TextField campoCodigoDeBarras;

    @FXML
    private TextField campoComposicao;

    @FXML
    private TextField campoNomeMedicamento;

    @FXML
    private TextField campoQuantidadePorCaixa;

    @FXML
    private Label labelErroQtd;

    //lote enviado da tela cadastro:
    private Date validade;
    private int quantidadePorCartela;

    @FXML
    void acaoCancelar(ActionEvent event) {
      try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void acaoSalvar(ActionEvent event) {
      String textoQtd = campoQuantidadePorCaixa.getText();
        if (!textoQtd.matches("\\d+")) {
            
            labelErroQtd.setVisible(true);
            labelErroQtd.setText("A quantidade deve conter apenas números!");
            
            return; 
        }
       
        String nome = campoNomeMedicamento.getText();
        int quantidade = Integer.parseInt(textoQtd);
        String composicao  =  campoComposicao.getText();
        int codigoDeBarras = Integer.parseInt(campoCodigoDeBarras.getText());
        // Aqui vai vir a logica de add no BD por ex, como nao tem ainda, e uma simulacao de como seriam os campos etc etc
        System.out.println("Salvando Lote...");
        System.out.println("Medicamento: " + nome);
        
        System.out.println("Qtd (Int): " + quantidade);
        
        System.out.println("Composicao: " + composicao);

        System.out.println("Codigo de Barras: " + codigoDeBarras);
        /*try {
          ControllerEstoque.adicionarMedicamento(campoNomeMedicamento.getText(), quantidade, campoComposicao.getText() , Integer.parseInt(campoCodigoDeBarras.getText()));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        } */
        //adiciona o medicamento (precisamos fazer a logica de verificar se o funcionario tem acesso a isso)
        try{
          ControllerTelas.getAcesso().adicionarMedicamento(ControllerTelas.getFuncionarioPadrao() , nome, quantidade, composicao, codigoDeBarras);
          System.out.println("Medicamento adicionado com sucesso!");

          if(this.validade != null){
            boolean sucesso = ControllerTelas.getAcesso().adicionarLoteEstoque(ControllerTelas.getFuncionarioPadrao(),
            nome, this.validade, this.quantidadePorCartela);
            
            if(sucesso){
              System.out.println("Lote adicionado com sucesso!");
            }else{
              System.out.println("Erro ao adicionar o lote associado ao medicamento cadastrado.");
            }
          }
          validade = null;
          quantidadePorCartela = 0;

        } catch (IOException e) {
          e.printStackTrace();
        } catch (Exception e) {
          System.out.println("Erro ao adicionar medicamento. Verifique os dados inseridos.");
          e.printStackTrace();
        }finally{
          acaoCancelar(event);
        } 
    }

    public void setDadosLote(String nome, Date validade, int quantidadePorCartela){
      campoNomeMedicamento.setText(nome);
      this.validade = validade;
      this.quantidadePorCartela = quantidadePorCartela;
    }

}
