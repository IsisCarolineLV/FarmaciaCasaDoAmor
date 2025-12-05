package controller.TelasControllers;

import java.io.IOException;
import java.sql.Date;

import controller.NotificacoesController;
import controller.EstoqueController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Funcionario;

public class TelaCadastroMedicamentoController{

    @FXML
    private TextField campoCodigoDeBarras;

    @FXML
    private TextField campoComposicao;

    @FXML
    private TextField campoNomeMedicamento;

    @FXML
    private TextField campoQuantidadePorCaixa;

    @FXML
    private Label labelErroQtd;

    //lote enviado da tela cadastro:
    private Date validadeLotePendente;
    private int qtdCaixasLotePendente;

    private EstoqueController service = new EstoqueController();

    @FXML
    void acaoCancelar(ActionEvent event) {
      try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void acaoSalvar(ActionEvent event) {
        String textoQtd = campoQuantidadePorCaixa.getText();
        if (!textoQtd.matches("\\d+")) {
            labelErroQtd.setVisible(true);
            labelErroQtd.setText("A quantidade deve conter apenas números!");
            return;
        }

        String nome = campoNomeMedicamento.getText();
        int qtdPorCaixa = Integer.parseInt(textoQtd); // Ex: 10
        String composicao = campoComposicao.getText();
        int codigoDeBarras = Integer.parseInt(campoCodigoDeBarras.getText());

        try {
            Funcionario funcionario = NotificacoesController.getFuncionarioPadrao();

            // 1. Salva o Medicamento primeiro
            boolean sucesso = service.cadastrarMedicamento(nome, qtdPorCaixa, composicao, codigoDeBarras, funcionario);

            if (sucesso) {
                System.out.println("Medicamento salvo no Banco de Dados");

                // 2. Se houver um lote pendente (vindo da tela anterior), salva agora
                if (this.validadeLotePendente != null) {

                    // Multiplica: 20 caixas * 10 por caixa = 200 comprimidos
                    int totalComprimidos = this.qtdCaixasLotePendente * qtdPorCaixa; 

                    boolean sucessoLote = service.cadastrarLote(nome, this.validadeLotePendente, totalComprimidos, funcionario);

                    if (sucessoLote) System.out.println("Lote salvo com Total de: " + totalComprimidos + " comprimidos.");
                }

                acaoCancelar(event);
            } else {
                System.out.println("Erro ao salvar medicamento.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDadosLote(String nome, Date validade, int quantidadePorCartela){
      campoNomeMedicamento.setText(nome);
      this.validadeLotePendente = validade;
      this.qtdCaixasLotePendente = quantidadePorCartela;
    }

}
