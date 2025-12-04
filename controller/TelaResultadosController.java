package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.List;

import modelo.Medicamento;

import java.io.IOException;

import service.FarmaciaService;

public class TelaResultadosController{

    @FXML
    private Label labelMensagem;

    private FarmaciaService service = new FarmaciaService();

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
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
            Stage stage = (Stage) labelMensagem.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}