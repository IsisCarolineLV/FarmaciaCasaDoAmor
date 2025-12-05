package controller.TelasControllers;

import java.util.List;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Medicamento;
import model.dao.MedicamentoDAOJdbc;

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
        controller.NotificacoesController.gerarNotificacoes();
        circleAtencao.setVisible(controller.NotificacoesController.temNotificacoes());
    }

    private void configurarTabela() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoDeBarras"));
        colQuantidade.setText("Qtd. Lotes"); 
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeLotes"));

        tabelaMedicamentos.setRowFactory(tableView -> {
            final TableRow<Medicamento> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();

            MenuItem itemVerLotes = new MenuItem("Ver Lotes");
            itemVerLotes.setOnAction(event -> {
                if (row.getItem() != null) abrirTelaLotes(row.getItem());
            });

            MenuItem itemExcluir = new MenuItem("Excluir Cadastro");
            itemExcluir.setStyle("-fx-text-fill: red;"); // Opcional: deixa o texto vermelho
            itemExcluir.setOnAction(event -> {
                if (row.getItem() != null) acaoExcluirCompleto(row.getItem());
            });

            MenuItem itemDados = new MenuItem("Ver Dados");
            itemDados.setOnAction(event -> {
                if (row.getItem() != null) abrirTelaMedicamento(row.getItem());
            });

            contextMenu.getItems().addAll(itemVerLotes, itemDados, itemExcluir);

            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                .then((ContextMenu)null)
                .otherwise(contextMenu)
            );

            return row;
        });
    }

    private void acaoExcluirCompleto(Medicamento med) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Medicamento");
        alert.setHeaderText("Você está prestes a excluir: " + med.getNome());
        alert.setContentText("Isso apagará o medicamento e TODOS os lotes associados a ele.\nEssa ação não pode ser desfeita. Tem certeza?");

        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            try {
                MedicamentoDAOJdbc dao = new MedicamentoDAOJdbc();

                dao.remover(med.getCodigoDeBarras());

                mostrarAlerta("Sucesso", "Medicamento e lotes excluídos com sucesso.");

                carregarDadosDoBanco();
                controller.NotificacoesController.gerarNotificacoes();
                circleAtencao.setVisible(controller.NotificacoesController.temNotificacoes());

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    // Abre a tela de visualização de lotes
    private void abrirTelaLotes(Medicamento med) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLotes.fxml"));
            Parent root = loader.load();
            
            // Passa o medicamento para o controller da nova tela
            TelaLotesController controller = loader.getController();
            controller.setMedicamento(med);
            
            Stage stage = (Stage) tabelaMedicamentos.getScene().getWindow();
            stage.setTitle("Gerenciamento de Lotes - " + med.getNome());
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

            //stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a tela de lotes.");
        }
    }

    private void abrirTelaMedicamento(Medicamento med) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaMedicamento.fxml"));
            Parent root = loader.load();
            
            // Passa o medicamento para o controller da nova tela
            TelaMedicamentoController controller = loader.getController();
            controller.setDadosMedicamento(med);
            
            Stage stage = (Stage) tabelaMedicamentos.getScene().getWindow();
            stage.setTitle("Gerenciamento de Lotes - " + med.getNome());
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

            //stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a tela de lotes.");
        }
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
        }
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
 
    @FXML public void irParaNotificacoes(MouseEvent event) { navegar(event, "/view/TelaNotificacoes.fxml"); }
    @FXML public void irParaCadastroLote(ActionEvent event) { navegar(event, "/view/TelaCadastroLote.fxml"); }
    @FXML public void irParaCadastroMedicamento(ActionEvent event) { navegar(event, "/view/TelaCadastroMedicamento.fxml"); }
    @FXML void irParaFuncionario(MouseEvent event) {navegar(event, "/view/TelaFuncionario.fxml"); }

    @FXML void irParaHistorico(ActionEvent event) {
        navegar(event, "/view/TelaHistorico.fxml");
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

            //stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
