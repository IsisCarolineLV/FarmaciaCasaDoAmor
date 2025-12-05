package controller.TelasControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaCadastroFuncionarioController {

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoNomeFuncionario;

    @FXML
    private Label labelErroQtd;

    @FXML
    void acaoCancelar(ActionEvent event) {
      navegar(event, "/view/TelaInicial.fxml");
    }

    @FXML
    void acaoSalvar(ActionEvent event) {
      navegar(event, "/view/TelaInicial.fxml");
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
