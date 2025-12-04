package controller;

import java.util.List;
import java.util.Optional;
import dao.MedicamentoDAOJdbc; 
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modelo.Medicamento;

public class TelaInicialController {

    @FXML private TextField campoPesquisa;
    @FXML private Circle circleAtencao;

    @FXML private TableView<Medicamento> tabelaMedicamentos;
    @FXML private TableColumn<Medicamento, String> colNome;
    @FXML private TableColumn<Medicamento, Integer> colCodigo;
    @FXML private TableColumn<Medicamento, Integer> colQuantidade;

    private ObservableList<Medicamento> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDadosDoBanco();
        configurarFiltroPesquisa();
        
        if (circleAtencao != null) circleAtencao.setVisible(false);
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoDeBarras"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadePorCartela"));

        tabelaMedicamentos.setRowFactory(tableView -> {
            final TableRow<Medicamento> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            
            MenuItem itemVerLotes = new MenuItem("Ver Lotes");
            itemVerLotes.setOnAction(event -> {
                if (row.getItem() != null) abrirTelaLotes(row.getItem());
            });
            MenuItem itemRemover = new MenuItem("Remover Estoque");
            itemRemover.setOnAction(event -> {
                if (row.getItem() != null) acaoRetirarEstoque(row.getItem());
            });
            
            contextMenu.getItems().addAll(itemVerLotes, itemRemover);

            // Exibir apenas se a linha não estiver vazia
            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                .then((ContextMenu)null)
                .otherwise(contextMenu)
            );
            
            return row;
        });
    }

    // Abre a tela de visualização de lotes
    private void abrirTelaLotes(Medicamento med) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaLotes.fxml"));
            Parent root = loader.load();
            
            // Passa o medicamento para o controller da nova tela
            TelaLotesController controller = loader.getController();
            controller.setMedicamento(med);
            
            Stage stage = new Stage();
            stage.setTitle("Gerenciamento de Lotes - " + med.getNome());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a tela de lotes.");
        }
    }

    private void acaoRetirarEstoque(Medicamento med) {
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Retirada de Estoque");
        dialog.setHeaderText("Retirar itens de: " + med.getNome());
        dialog.setContentText("Quantidade a remover:");

        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(quantidadeString -> {
            try {
                int qtdRetirar = Integer.parseInt(quantidadeString);
                
                if (qtdRetirar <= 0) {
                    mostrarAlerta("Erro", "Digite um número maior que zero.");
                    return;
                }
                
                if (qtdRetirar > med.getQuantidadePorCartela()) {
                    mostrarAlerta("Erro", "Estoque insuficiente! Atual: " + med.getQuantidadePorCartela());
                    return;
                }

                int novaQtd = med.getQuantidadePorCartela() - qtdRetirar;
                MedicamentoDAOJdbc dao = new MedicamentoDAOJdbc();

                if (novaQtd == 0) {
                    dao.remover(med.getCodigoDeBarras());
                    mostrarAlerta("Sucesso", "Estoque zerado. Medicamento removido.");
                } else {
                    dao.atualizarQuantidade(med.getCodigoDeBarras(), novaQtd);
                    mostrarAlerta("Sucesso", "Retirados " + qtdRetirar + " itens.");
                }

                carregarDadosDoBanco();

            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "Digite apenas números inteiros.");
            } catch (Exception e) {
                mostrarAlerta("Erro Crítico", e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void carregarDadosDoBanco() {
        try {
            MedicamentoDAOJdbc dao = new MedicamentoDAOJdbc(); 
            List<Medicamento> lista = dao.listarTodos();
            
            masterData.clear();
            if (lista != null) masterData.addAll(lista);
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void configurarFiltroPesquisa() {
        FilteredList<Medicamento> filteredData = new FilteredList<>(masterData, p -> true);

        campoPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(med -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lower = newValue.toLowerCase();
                if (med.getNome().toLowerCase().contains(lower)) return true;
                return String.valueOf(med.getCodigoDeBarras()).contains(lower);
            });
        });

        SortedList<Medicamento> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tabelaMedicamentos.comparatorProperty());
        tabelaMedicamentos.setItems(sortedData);
    }
 
    @FXML public void irParaNotificacoes(MouseEvent event) { navegar(event, "/gui/TelaNotificacoes.fxml"); }
    @FXML public void irParaCadastroLote(ActionEvent event) { navegar(event, "/gui/TelaCadastroLote.fxml"); }
    @FXML public void irParaCadastroMedicamento(ActionEvent event) { navegar(event, "/gui/TelaCadastroMedicamento.fxml"); }
    @FXML void irParaHistorico(ActionEvent event) {
        if(ControllerTelas.getAcesso() != null) System.out.println(ControllerTelas.getAcesso().imprimirHistorico());
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
        }*/
    }
}
