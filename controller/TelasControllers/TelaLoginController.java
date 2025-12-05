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
import model.Funcionario; // Importe o seu modelo Funcionario

public class TelaLoginController {

    @FXML
    private TextField nomeField;
    
    @FXML
    private TextField cpfField;
    
    @FXML
    private Label labelErro;
    
    private FuncionarioController service = new FuncionarioController();

    @FXML
    void acaoLogin(ActionEvent event) {
        labelErro.setVisible(false);
        String nome = nomeField.getText();
        String cpf = cpfField.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            labelErro.setText("Preencha todos os campos.");
            labelErro.setVisible(true);
            return;
        }

        try {
            // 1. Validar login e obter o objeto Funcionario completo
            Funcionario funcionarioLogado = service.validarLoginEBuscar(nome, cpf); 

            if (funcionarioLogado != null) {
                System.out.println("Login bem-sucedido!");
                controller.NotificacoesController.setFuncionarioResponsavel(funcionarioLogado);
                navegarComDados(event, "/view/TelaFuncionario.fxml", funcionarioLogado);
            } else {
                labelErro.setText("Nome de usuário ou CPF incorretos.");
                labelErro.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            labelErro.setText("Erro interno ao tentar o login.");
            labelErro.setVisible(true);
        }
    }

    @FXML
    void acaoCancelar(ActionEvent event) {
        navegar(event, "/view/TelaInicial.fxml");
    }

    // Método de navegação simples (para telas sem dados)
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

    // NOVO MÉTODO DE NAVEGAÇÃO COM PASSAGEM DE DADOS
    private void navegarComDados(ActionEvent event, String fxmlPath, Funcionario funcionario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            
            TelaFuncionarioController controllerFuncionario = loader.getController();
            
            controllerFuncionario.setFuncionarioLogado(funcionario);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}