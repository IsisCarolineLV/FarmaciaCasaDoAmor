package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class TelaCadastroLoteController{

    @FXML private TextField campoNomeMedicamento;
    @FXML private TextField campoQuantidade;
    @FXML private DatePicker campoValidade;
    @FXML private Label labelErroQtd;

    @FXML
    public void acaoSalvar(ActionEvent event) {
        String textoQtd = campoQuantidade.getText();
        if (!textoQtd.matches("\\d+")) {
            
            labelErroQtd.setVisible(true);
            labelErroQtd.setText("A quantidade deve conter apenas números!");
            
            return; 
        }

        labelErroQtd.setVisible(false);
        LocalDate localDate = campoValidade.getValue();
        //se formos usar a logica de um so estoque pra tudo, descomentar a linha abaixo
        //ControllerEstoque.adicionarLote(campoNomeMedicamento.getText(), Integer.parseInt(textoQtd), Date.valueOf(localDate));
        //System.out.println( ControllerEstoque.imprimirLotes());

        // Aqui vai vir a logica de add no BD por ex, como nao tem ainda, e uma simulacao de como seriam os campos etc etc

        System.out.println("Salvando Lote...");
        System.out.println("Medicamento: " + campoNomeMedicamento.getText());
        
        int quantidade = Integer.parseInt(textoQtd); 
        System.out.println("Qtd (Int): " + quantidade);
        
        System.out.println("Validade: " + campoValidade.getValue());

        //adiciona o lote (precisamos fazer a logica de verificar se o funcionario tem acesso a isso)
        boolean sucesso = ControllerTelas.getAcesso().adicionarLoteEstoque(campoNomeMedicamento.getText(), Date.valueOf(localDate), quantidade);
        if(!sucesso){
            System.out.println("Medicamento nao cadastrado, cadastre primeiro");
            chamaTelaMedicamento(campoNomeMedicamento.getText(), Date.valueOf(localDate), quantidade);
        }else{
            System.out.println("Lote adicionado com sucesso!");
            acaoCancelar(event);
        }
        
    }

    @FXML
    public void acaoCancelar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chamaTelaMedicamento(String nome, Date validade, int quantidadePorCartela){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaCadastroMedicamento.fxml"));
            Parent root = loader.load();

            TelaCadastroMedicamentoController controller = loader.getController();
            controller.setDadosLote(nome,validade, quantidadePorCartela );

            Stage stage = (Stage) campoNomeMedicamento.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tentarInserirLoteDeNovo(){
        String textoQtd = campoQuantidade.getText();
        labelErroQtd.setVisible(false);
        LocalDate localDate = campoValidade.getValue();
        //System.out.println("Salvando Lote...");
        //System.out.println("Medicamento: " + campoNomeMedicamento.getText());
        
        int quantidade = Integer.parseInt(textoQtd); 
        //System.out.println("Qtd (Int): " + quantidade);
        
        //System.out.println("Validade: " + campoValidade.getValue());

        //adiciona o lote (precisamos fazer a logica de verificar se o funcionario tem acesso a isso)
        boolean sucesso = ControllerTelas.getAcesso().adicionarLoteEstoque(campoNomeMedicamento.getText(), Date.valueOf(localDate), quantidade);
        if(!sucesso){
            System.out.println("Medicamento nao cadastrado, cadastre primeiro");
            chamaTelaMedicamento(campoNomeMedicamento.getText(), Date.valueOf(localDate), quantidade);
        }else{
            System.out.println("Lote adicionado com sucesso!");
          acaoCancelar(new ActionEvent());
        }
    }
}