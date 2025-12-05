package controller.TelasControllers;

import controller.FuncionarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Funcionario;

public class TelaCadastroFuncionarioController {

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoNomeFuncionario;

    @FXML
    private Label labelErro;

    private FuncionarioController service = new FuncionarioController();
    private String novoNome;
    private String novoCPF;

    @FXML
    void acaoCancelar(ActionEvent event) {
      navegar(event, "/view/TelaInicial.fxml");
    }

    @FXML
    void acaoSalvar(ActionEvent event) {
      labelErro.setVisible(false);
      try{
          if(campoCPF.getText().isEmpty() || campoNomeFuncionario.getText().isEmpty()){
            labelErro.setText("Preencha todos os campos!");
            labelErro.setVisible(true); 
            return;
          }
          boolean jaExiste = service.funcionarioJaCadastrado(campoCPF.getText());
          if(jaExiste){
            labelErro.setText("CPF já cadastrado!");
            labelErro.setVisible(true); 
            return;
          }
          boolean sucesso = service.cadastrarFuncionario(campoNomeFuncionario.getText(),campoCPF.getText());
          if(sucesso){
            System.out.println("Novo funcionario salvo no Banco de Dados");
            acaoCancelar(event);
          } else{
            System.out.println("Erro ao salvar");
          }
        } catch(Exception e){
          e.printStackTrace();
        }
    }

    private void navegar(javafx.event.Event event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
