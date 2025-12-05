package controller.TelasControllers;

import controller.EstoqueController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  @FXML
  void acaoEditar(ActionEvent event) {
    //chama tela edicao medicamento
  }

  @FXML
  void acaoVerLotes(ActionEvent event) {
    //chama tela lotes
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
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    labelNome.setText(medReal.getNome());
    labelCodigoBarras.setText(Integer.toString(medReal.getCodigoDeBarras()));
    labelComposicao.setText(medReal.getComposicao());
    labelEstoqueCaixas.setText(Integer.toString(medReal.getQuantidadePorCartela()));
    labelEstoqueComprimidos.setText(Integer.toString(medReal.getQuantidadeLotes()*m.getQuantidadePorCartela()));
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

}
