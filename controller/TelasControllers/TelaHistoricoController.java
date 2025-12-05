package controller.TelasControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Historico;

import java.io.IOException;
import java.util.List;

import controller.EstoqueController;

public class TelaHistoricoController {

    @FXML
    private TableView<Historico> historicoTable;
    @FXML
    private TableColumn<Historico, String> dataColumn; 
    @FXML
    private TableColumn<Historico, String> horaColumn; 
    @FXML
    private TableColumn<Historico, String> funcionarioColumn;
    @FXML
    private TableColumn<Historico, String> acaoColumn;

    private final EstoqueController estoqueController = new EstoqueController();

    @FXML
    public void initialize() {
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("dataDia"));
        horaColumn.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        funcionarioColumn.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario")); 
        acaoColumn.setCellValueFactory(new PropertyValueFactory<>("acao"));

        carregarHistorico();
    }

    private void carregarHistorico() {
        try {
            List<Historico> lista = estoqueController.buscarHistoricoCompleto();
            historicoTable.getItems().setAll(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaInicial.fxml"));
    		Parent root = loader.load();
    		Stage stage = (Stage) historicoTable.getScene().getWindow();
    		stage.setScene(new Scene(root));
            stage.setTitle("Estoque - Casa do Amor"); // Ajuste o título se necessário
            stage.show();
    	} catch (IOException e) {
            System.err.println("Erro ao carregar TelaInicial.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    	
    	}
    }