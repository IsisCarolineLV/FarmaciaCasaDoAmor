package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    private Label labelQuantidade;

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
    }

}
