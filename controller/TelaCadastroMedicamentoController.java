package controller;

import java.io.IOException;
import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import service.FarmaciaService;
import modelo.Funcionario;

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
    private Date validade;
    private int quantidadePorCartela;

    private FarmaciaService service = new FarmaciaService();

    @FXML
    void acaoCancelar(ActionEvent event) {
      try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/TelaInicial.fxml"));
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
        int quantidade = Integer.parseInt(textoQtd);
        String composicao  =  campoComposicao.getText();
        int codigoDeBarras = Integer.parseInt(campoCodigoDeBarras.getText());
        
        try{
          Funcionario funcionario = ControllerTelas.getFuncionarioPadrao();
          boolean sucesso = service.cadastrarMedicamento(nome, quantidade, composicao, codigoDeBarras, funcionario);

          if(sucesso){
            System.out.println("Medicamento salvo no Banco de Dados");

            if(this.validade != null){
              boolean sucessoLote = service.cadastrarLote(nome, codigoDeBarras, this.validade, this.quantidadePorCartela, funcionario);
              if(sucessoLote) System.out.println("Lote salvo no Banco de Dados");
            }

            acaoCancelar(event);
          } else{
            System.out.println("Erro ao salvar");
          }
        } catch(Exception e){
          e.printStackTrace();
        }
    }

    public void setDadosLote(String nome, Date validade, int quantidadePorCartela){
      campoNomeMedicamento.setText(nome);
      this.validade = validade;
      this.quantidadePorCartela = quantidadePorCartela;
    }

}
