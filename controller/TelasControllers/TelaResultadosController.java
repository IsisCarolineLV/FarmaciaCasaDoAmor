package controller.TelasControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Medicamento;

import java.util.List;

import controller.EstoqueController;

import java.io.IOException;

public class TelaResultadosController{

    @FXML
    private Label labelMensagem;

    private EstoqueController service = new EstoqueController();

    public void definirTermoPesquisado(String termo) {
        System.out.println("Pesquisando por: " + termo);

        List<Medicamento> resultados = service.buscarMedicamentos(termo);

        if(resultados.isEmpty()){
            labelMensagem.setText("Nenhum medicamento encontrado");
        } else{
            StringBuilder texto = new StringBuilder("Encontrados:\n");
            for(Medicamento m : resultados){
                texto.append("- ").append(m.getNome()).append("\n");
            }
            labelMensagem.setText(texto.toString());
        }
    }

    @FXML
    public void acaoVoltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
            Stage stage = (Stage) labelMensagem.getScene().getWindow();
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

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}