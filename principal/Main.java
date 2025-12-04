package principal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
        
        primaryStage.setTitle("Farmácia - Tela Inicial");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //DatabaseSetup.inicializar(); //Inicializa automaticamente o bd da aplicacao
        launch(args);
    }
}