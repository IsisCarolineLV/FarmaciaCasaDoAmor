package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modelo.Acesso;
//import modelo.Estoque;

public class TelaInicialController{

    @FXML
    private TextField campoPesquisa;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Circle circleAtencao;

    @SuppressWarnings("unused")
	private Acesso sistemaAcesso;

    public void setSistemaAcesso(Acesso acesso) {
        this.sistemaAcesso = acesso;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Inicial carregada.");
        ControllerTelas.gerarNotificacoes();
        if(ControllerTelas.temNotificacoes()) {
        	circleAtencao.setVisible(true);
        } else {
        	circleAtencao.setVisible(false);
        }
        //ControllerTelas telas = new ControllerTelas();
        //ControllerEstoque.setEstoque(new Estoque());
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
        irPraNovaTela(event, "/gui/TelaNotificacoes.fxml");
        /*try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaNotificacoes.fxml"));
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            
        } catch (Exception e) {
            System.out.println("Erro ao carregar notificações.");
            e.printStackTrace();
        }*/
    }

    @FXML
    public void irParaCadastroLote(javafx.event.ActionEvent event) {
        irPraNovaTela(event, "/gui/TelaCadastroLote.fxml");
        /*try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaCadastroLote.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de cadastro.");
            e.printStackTrace();
        }*/
    }

    @FXML
    public void irParaCadastroMedicamento(javafx.event.ActionEvent event) {
        irPraNovaTela(event, "/gui/TelaCadastroMedicamento.fxml");
        /*try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaCadastroMedicamento.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            //stage.setScene(new Scene(root, 900, 600));
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de cadastro de medicamento.");
            e.printStackTrace();
        }*/
    }

    @FXML
    void irParaHistorico(ActionEvent event) {
        //chamar tela de historico
        System.out.println(ControllerTelas.getAcesso().imprimirHistorico());
    }

    //criei um metodo especifico para ele trocar de tela mantendo o tamanho da janela anterior
    public void irPraNovaTela(Event event, String caminhoTela){
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminhoTela));
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
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela "+caminhoTela);
            e.printStackTrace();
        }
    }
}