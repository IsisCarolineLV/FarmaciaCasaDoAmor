package controller.TelasControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import controller.NotificacoesController;
import javafx.event.ActionEvent;

public class TelaNotificacoesController{
	@FXML
    private Label labelAviso; 

    @FXML
    private VBox paneNotificacoes;

    @FXML
    public void initialize() { // funcaozinha que simula a tela de notificaçoes,
    						   // atualmente so envia que nao tem not
        
        if (!NotificacoesController.temNotificacoes()) {
            labelAviso.setText("Não há notificações novas");
        } else {
            paneNotificacoes.setAlignment(javafx.geometry.Pos.TOP_CENTER);
            ArrayList<Pane> notificacoes = NotificacoesController.gerarNotificacoes();
            for(Pane p: notificacoes){
                paneNotificacoes.getChildren().add(p);
            }
            //labelAviso.setText("Você tem itens pendentes!");
        }
    }
    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
