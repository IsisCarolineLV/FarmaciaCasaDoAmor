package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class TelaResultadosController {

    @FXML
    private Label labelMensagem;

    public void definirTermoPesquisado(String termo) {
        // Ta exibindo uma msg fixa pq ainda nao tem a logica com o acesso, bd etc etc
        System.out.println("Pesquisando por: " + termo);
        labelMensagem.setText("Nenhum medicamento encontrado");
    }

    @FXML
    public void acaoVoltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
            Stage stage = (Stage) labelMensagem.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}