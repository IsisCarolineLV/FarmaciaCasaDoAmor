package controller.TelasControllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import controller.dao.LoteDAOJdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Lote;
import model.Medicamento;

public class TelaLotesController implements Initializable {

    @FXML private Label lblTitulo;
    @FXML private TableView<Lote> tabelaLotes;
    @FXML private TableColumn<Lote, Integer> colId;
    @FXML private TableColumn<Lote, Date> colValidade;
    @FXML private TableColumn<Lote, Integer> colQtd;

    private Medicamento medicamentoAtual;
    private LoteDAOJdbc loteDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loteDAO = new LoteDAOJdbc();
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote")); 
        colValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidadeComprimidos"));
    }

    public void setMedicamento(Medicamento med) {
        this.medicamentoAtual = med;
        lblTitulo.setText("Lotes: " + med.getNome()); 
        carregarDados();
    }

    private void carregarDados() {
        if (medicamentoAtual != null) {
            try {
                ObservableList<Lote> lista = FXCollections.observableArrayList(
                    loteDAO.listarPorMedicamento(medicamentoAtual.getCodigoDeBarras())
                );
                tabelaLotes.setItems(lista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleExcluirLote() {
        // Lógica de exclusão aqui 
        System.out.println("Implementar exclusão");
    }

    @FXML
    private void voltarParaInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml")); //
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
