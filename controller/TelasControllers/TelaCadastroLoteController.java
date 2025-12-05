package controller.TelasControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Funcionario;
import model.Medicamento;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;

import controller.NotificacoesController;
import controller.EstoqueController;

public class TelaCadastroLoteController{

    @FXML private TextField campoNomeMedicamento;
    @FXML private TextField campoQuantidade;
    @FXML private DatePicker campoValidade;
    @FXML private Label labelErroQtd;

    private EstoqueController service = new EstoqueController();

    @FXML
    public void acaoSalvar(ActionEvent event) {
        String textoQtd = campoQuantidade.getText();
        if (!textoQtd.matches("\\d+")) {
            labelErroQtd.setVisible(true);
            labelErroQtd.setText("A quantidade deve conter apenas números!");
            return; 
        }

        labelErroQtd.setVisible(false);

        try {
            String nomeMedicamento = campoNomeMedicamento.getText();
            int qtdCaixas = Integer.parseInt(textoQtd); 
            Date dataValidade = Date.valueOf(campoValidade.getValue());

            Funcionario funcionario = NotificacoesController.getFuncionarioPadrao();

            Medicamento medExistente = service.buscarPorNome(nomeMedicamento); 

            if (medExistente != null) {
                // Se existe, calculamos o TOTAL real
                int totalComprimidos = qtdCaixas * medExistente.getQuantidadePorCartela();

                // Salva o TOTAL (200) e não as caixas (20)
                boolean sucesso = service.cadastrarLote(
                    nomeMedicamento,
                    dataValidade,
                    totalComprimidos, 
                    funcionario
                );

                if (sucesso) {
                    System.out.println("Lote cadastrado! Total un: " + totalComprimidos);
                    acaoCancelar(event);
                }
            } else {
                // Se não existe, redireciona para criar o medicamento
                System.out.println("Medicamento não encontrado. Redirecionando...");
                // Passamos 'qtdCaixas' para a próxima tela fazer a conta lá
                chamaTelaMedicamento(nomeMedicamento, dataValidade, qtdCaixas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void acaoCancelar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chamaTelaMedicamento(String nome, Date validade, int quantidadePorCartela){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TelaCadastroMedicamento.fxml"));
            Parent root = loader.load();

            TelaCadastroMedicamentoController controller = loader.getController();
            controller.setDadosLote(nome,validade, quantidadePorCartela );

            Stage stage = (Stage) campoNomeMedicamento.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}