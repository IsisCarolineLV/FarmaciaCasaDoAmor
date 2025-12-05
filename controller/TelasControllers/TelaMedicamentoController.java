package controller.TelasControllers;

import controller.EstoqueController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Medicamento;

public class TelaMedicamentoController {

  @FXML
  private Label labelCodigoBarras;

  @FXML
  private Label labelComposicao;

  @FXML
  private Label labelEstoqueCaixas;

  @FXML
  private Label labelEstoqueComprimidos;

  @FXML
  private Label labelNome;

  @FXML
  private Label labelTitulo;

  @FXML
  private Label labelQuantidade;

  private EstoqueController service = new EstoqueController();
  private Medicamento med;


  @FXML
void acaoVerLotes(ActionEvent event) {
    try {
        // 1. Carrega o arquivo FXML da tela de Lotes (Verifique se o nome do arquivo está correto!)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaLotes.fxml"));
        Parent root = loader.load();

        // 2. PASSO CRUCIAL: Pega o controlador da tela de Lotes para passar o medicamento
        // Atenção: Você precisa ter um Controller para a tela de lotes (ex: TelaLotesController)
        TelaLotesController controller = loader.getController();
        controller.setMedicamento(this.med); // Passa o objeto 'med' atual para a próxima tela

        // 3. Pega o palco (Stage) atual usando o evento do botão (forma genérica e segura)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Configurações visuais (mantendo sua lógica de manter tamanho/fullscreen)
        stage.setTitle("Gerenciamento de Lotes - " + med.getNome());
        boolean fullscreen = stage.isFullScreen();
        boolean maximizado = stage.isMaximized();
        double largura = stage.getWidth();
        double altura = stage.getHeight();

        stage.setScene(new Scene(root));

        if (maximizado || fullscreen){
            stage.setFullScreen(fullscreen);
            stage.setMaximized(maximizado);
        } else {
            stage.setWidth(largura);
            stage.setHeight(altura);
        }

    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Erro", "Não foi possível abrir a tela de lotes: " + e.getMessage());
    }
}

  @FXML
  void acaoVoltar(ActionEvent event) {
    //volta pra tela inicial
    navegar(event, "/view/TelaInicial.fxml");
  }

  public void setDadosMedicamento(Medicamento m){
    Medicamento medReal;
    try {
      medReal = service.buscarPorCodigoDeBarras(m.getCodigoDeBarras());
      med = medReal;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    
    // Define os textos básicos
    labelTitulo.setText("MEDICAMENTO: " + medReal.getNome().toUpperCase());
    labelNome.setText(medReal.getNome());
    labelCodigoBarras.setText(Integer.toString(medReal.getCodigoDeBarras()));
    labelComposicao.setText(medReal.getComposicao());
    
    // --- CÁLCULO DE ESTOQUE CORRIGIDO ---
    
    // 1. Busca o total real de comprimidos somando todos os lotes (agora funciona!)
    int totalComprimidos = service.buscarEstoqueTotal(medReal.getCodigoDeBarras());
    
    // 2. Recupera quantos comprimidos vêm em 1 caixa (vindo da tabela Medicamento)
    int qtdPorCaixa = medReal.getQuantidadePorCartela();
    
    // 3. Calcula quantas caixas completas nós temos
    int totalCaixas = (qtdPorCaixa > 0) ? totalComprimidos / qtdPorCaixa : 0;
    
    // 4. Exibe na tela
    labelEstoqueComprimidos.setText(totalComprimidos + " comprimidos");
    labelEstoqueCaixas.setText(totalCaixas + (totalCaixas == 1 ? " caixa" : " caixas"));
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
