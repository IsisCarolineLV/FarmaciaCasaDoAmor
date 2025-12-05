package controller.TelasControllers;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.EstoqueController; 
import controller.dao.LoteDAOJdbc;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Lote;
import model.Medicamento;

public class TelaLotesController implements Initializable {

    @FXML private Label lblTitulo;
    @FXML private TableView<Lote> tabelaLotes;
    @FXML private TableColumn<Lote, Integer> colId;
    @FXML private TableColumn<Lote, Date> colValidade;
    @FXML private TableColumn<Lote, Integer> colTotalReal;
    @FXML private TableColumn<Lote, Integer> colInfoQtdPorCaixa; 
    @FXML private TableColumn<Lote, String> colQtdCaixas;

    private Medicamento medicamentoAtual;
    private LoteDAOJdbc loteDAO; 
    private EstoqueController estoqueController; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loteDAO = new LoteDAOJdbc();
        estoqueController = new EstoqueController(); 
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        colValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
        colTotalReal.setCellValueFactory(new PropertyValueFactory<>("quantidadeComprimidos"));
        
        configurarMenuDeContexto();
    }

    public void setMedicamento(Medicamento med) {
        this.medicamentoAtual = med;
        lblTitulo.setText("Estoque: " + med.getNome());

        if (colInfoQtdPorCaixa != null) {
            colInfoQtdPorCaixa.setCellValueFactory(cellData -> 
                new SimpleObjectProperty<>(med.getQuantidadePorCartela()));
        }

        if (colQtdCaixas != null) {
            colQtdCaixas.setCellValueFactory(cellData -> {
                Lote lote = cellData.getValue();
                double total = (double) lote.getQuantidadeComprimidos();
                double porCaixa = (double) med.getQuantidadePorCartela();
                if (porCaixa > 0) {
                    int caixas = (int) Math.ceil(total / porCaixa);
                    return new SimpleStringProperty(String.valueOf(caixas));
                }
                return new SimpleStringProperty("0");
            });
        }
        
        carregarDados();
    }

    private void configurarMenuDeContexto() {
        tabelaLotes.setRowFactory(tv -> {
            TableRow<Lote> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem itemConsumir = new MenuItem("Consumir...");
            itemConsumir.setStyle("-fx-font-weight: bold;");
            itemConsumir.setOnAction(e -> abrirDialogoConsumo(row.getItem()));

            MenuItem itemExcluir = new MenuItem("Excluir Lote");
            itemExcluir.setStyle("-fx-text-fill: red;");
            itemExcluir.setOnAction(e -> excluirLoteDoBanco(row.getItem()));

            contextMenu.getItems().addAll(itemConsumir, itemExcluir);

            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                .then((ContextMenu)null)
                .otherwise(contextMenu)
            );
            return row;
        });
    }

    private void abrirDialogoConsumo(Lote lote) {
        if (lote == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Consumir Medicamento");
        dialog.setHeaderText("Lote ID: " + lote.getIdLote());
        dialog.setContentText("Quantidade a remover (Máx: " + lote.getQuantidadeComprimidos() + "):");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(qtdString -> {
            try {
                int qtd = Integer.parseInt(qtdString);
                
                String erro = estoqueController.consumirDoLote(lote, qtd);

                if (erro == null) {
                    carregarDados(); 
                } else {
                    mostrarErro(erro);
                }
            } catch (NumberFormatException e) {
                mostrarErro("Número inválido.");
            }
        });
    }

    private void carregarDados() {
        if (medicamentoAtual != null) {
            try {
                ObservableList<Lote> lista = FXCollections.observableArrayList(
                    loteDAO.listarPorMedicamento(medicamentoAtual.getCodigoDeBarras())
                );
                tabelaLotes.setItems(lista);
                tabelaLotes.refresh();
            } catch (Exception e) {
                mostrarErro("Erro ao listar: " + e.getMessage());
            }
        }
    }
    
    // ESTE É O MÉTODO QUE ESTAVA FALTANDO PARA O BOTÃO FUNCIONAR
    @FXML
    private void handleExcluirLote(ActionEvent event) {
        Lote selecionado = tabelaLotes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            excluirLoteDoBanco(selecionado);
        } else {
            mostrarErro("Selecione um lote na tabela para excluir.");
        }
    }

    private void excluirLoteDoBanco(Lote lote) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja excluir este lote?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    loteDAO.excluir(lote.getIdLote());
                    carregarDados();
                } catch (Exception e) {
                    mostrarErro("Erro ao excluir: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void voltarParaInicio(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}