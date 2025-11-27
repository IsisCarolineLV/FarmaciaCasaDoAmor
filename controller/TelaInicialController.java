package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Acesso;
import modelo.Estoque;

public class TelaInicialController {

    @FXML
    private TextField campoPesquisa;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnCancelar;

    @SuppressWarnings("unused")
	private Acesso sistemaAcesso;

    public void setSistemaAcesso(Acesso acesso) {
        this.sistemaAcesso = acesso;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Inicial carregada.");
        ControllerEstoque.setEstoque(new Estoque());
    }

    @FXML
    public void acaoPesquisar() {
        String termo = campoPesquisa.getText();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaResultados.fxml"));
            Parent root = loader.load();
            TelaResultadosController controller = loader.getController();
            controller.definirTermoPesquisado(termo); 
            
            // 3. Trocar a Cena
            Stage stage = (Stage) btnPesquisar.getScene().getWindow(); 
            stage.setScene(new Scene(root, 900, 600)); 
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar a tela de resultados.");
            e.printStackTrace();
        }
    }

    @FXML
    public void acaoCancelar() {
        campoPesquisa.clear();
        System.out.println("Campos limpos.");
    }
    
    @FXML
    public void irParaNotificacoes(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaNotificacoes.fxml"));
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar notificações.");
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaCadastro(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaCadastroLote.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de cadastro.");
            e.printStackTrace();
        }
    }
}