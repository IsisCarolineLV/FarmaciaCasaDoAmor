package controller.TelasControllers;

import controller.NotificacoesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Funcionario; // Importe o modelo Funcionario

public class TelaFuncionarioController {

    @FXML
    private Label labelCPF;

    @FXML
    private Label labelErroQtd;

    @FXML
    private Label labelNome;

    private Funcionario funcionarioLogado; // Armazena o objeto

    @FXML
    public void initialize() {
        // Busca o funcionário logado globalmente, caso não tenha sido passado por setFuncionarioLogado
        Funcionario f = NotificacoesController.getFuncionarioResponsavel();
        if (f != null) {
            setDadosFuncionario(f);
        }
    }
    
    // NOVO MÉTODO: Recebe o objeto do login e atualiza a tela
    public void setFuncionarioLogado(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;
        if (funcionario != null) {
            labelNome.setText(funcionario.getNome().toUpperCase());
            // Assumindo que seu getCPF retorna o CPF formatado ou não
            labelCPF.setText(funcionario.getCPF()); 
            
            // Opcional: Se houver método initialize(), você pode chamar aqui se necessário
            // initialize();
        }
    }
    public void setDadosFuncionario(Funcionario funcionario) {
        if (funcionario != null) {
            labelNome.setText(funcionario.getNome());
            labelCPF.setText(funcionario.getCPF());
        }
    }

    @FXML
    void acaoTrocar(ActionEvent event) {
        navegar(event, "/view/TelaLogin.fxml");
    }

    @FXML
    void acaoVoltar(ActionEvent event) {
        navegar(event, "/view/TelaInicial.fxml");
    }
    
    @FXML
    void acaoCadastrar(ActionEvent event) {
        navegar(event, "/view/TelaCadastroFuncionario.fxml");
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
        }
    }
}
