package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TelaCadastroMedicamentoController {

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

    @FXML
    void acaoCancelar(ActionEvent event) {
      String textoQtd = campoQuantidadePorCaixa.getText();
        if (!textoQtd.matches("\\d+")) {
            
            labelErroQtd.setVisible(true);
            labelErroQtd.setText("A quantidade deve conter apenas números!");
            
            return; 
        }
       
        // Aqui vai vir a logica de add no BD por ex, como nao tem ainda, e uma simulacao de como seriam os campos etc etc
        System.out.println("Salvando Lote...");
        System.out.println("Medicamento: " + campoNomeMedicamento.getText());
        
        int quantidade = Integer.parseInt(textoQtd); 
        System.out.println("Qtd (Int): " + quantidade);
        
        System.out.println("Composicao: " + campoComposicao.getText());

        System.out.println("Codigo de Barras: " + campoCodigoDeBarras.getText());
        try {
          ControllerEstoque.adicionarMedicamento(campoNomeMedicamento.getText(), quantidade, campoComposicao.getText() , Integer.parseInt(campoCodigoDeBarras.getText()));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        } 
        acaoCancelar(event);
    }

    @FXML
    void acaoSalvar(ActionEvent event) {

    }

}
