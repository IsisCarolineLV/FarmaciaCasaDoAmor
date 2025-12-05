package controller.TelasControllers;

import controller.NotificacoesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Funcionario;

public class TelaFuncionarioController {

    @FXML
    private Label labelCPF;


    @FXML
    private Label labelNome;

    // Adicione este método
    @FXML
    public void initialize() {
        // Busca o funcionário que foi salvo estaticamente
        Funcionario f = NotificacoesController.getFuncionarioPadrao();
        if (f != null) {
            setDadosFuncionario(f.getNome(), f.getCPF());
        }
    }

    @FXML
    void acaoTrocar(ActionEvent event) {
      navegar(event, "/view/TelaCadastroFuncionario.fxml");
    }

    @FXML
    void acaoVoltar(ActionEvent event) {
      navegar(event, "/view/TelaInicial.fxml");
    }

    private void navegar(javafx.event.Event event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            boolean fullscreen = stage.isFullScreen();
            boolean maximizado = stage.isMaximized();
            double largura = stage.getWidth();
            double altura = stage.getHeight();

            stage.setScene(new Scene(root));

            if (maximizado || fullscreen){
                stage.setFullScreen(fullscreen);
                stage.setMaximized(maximizado);
            }else{
              stage.setWidth(largura);
              stage.setHeight(altura);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDadosFuncionario(String nome, String cpf){
        labelNome.setText(nome);
        labelCPF.setText(cpf);
    }

}
