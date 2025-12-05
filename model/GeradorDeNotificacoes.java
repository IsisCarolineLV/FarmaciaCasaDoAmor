package model;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GeradorDeNotificacoes {

    private final double WIDTH = 578;
    private final double HEIGHT = 70;
    private final double ARC_WIDTH = 15;
    private final double ARC_HEIGHT = 15;
    private Color[] cores;
    private final double STROKE_WIDTH = 1;
    private boolean ehVermelha;

    public GeradorDeNotificacoes(boolean ehVermelha) {
        cores = new Color[]{Color.web("#e34f4f3c"), Color.web("#efefef00"), Color.web("#b81f1fb2"),
                            Color.web("#f7ea773d"), Color.web("#f7ea773d"), Color.web("#665c00")};
        this.ehVermelha = ehVermelha;
    }   

    public Pane criarNotificacao(String texto) {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH + 16, HEIGHT + 12); // inclui padding similar ao layoutX/layoutY

        // Retângulo de fundo
        Rectangle rect = new Rectangle(8, 6, WIDTH, HEIGHT);
        rect.setArcWidth(ARC_WIDTH);
        rect.setArcHeight(ARC_HEIGHT);
        if(ehVermelha){
            rect.setFill(cores[0]);
            rect.setStroke(cores[1]);
        }else{
            rect.setFill(cores[3]);
            rect.setStroke(cores[4]);
        }
        rect.setStrokeWidth(STROKE_WIDTH);
        rect.setStyle("-fx-cursor: hand;");

        // Label
        Label label = new Label(texto);
        label.setLayoutX(30);
        label.setLayoutY(31);
        if(ehVermelha){
            label.setTextFill(cores[2]);
        }else{
            label.setTextFill(cores[5]);
        }
        label.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));

        // Eventos
        //rect.setOnMouseClicked(GeradorDeNotificacoes::verLote);
        rect.setOnMouseEntered(criarMudaCor(ehVermelha));
        rect.setOnMouseExited(criarVoltaCorOriginal(ehVermelha));
        if(ehVermelha){
            rect.setFill(Color.web("#FF000020"));
        }else{
            rect.setFill(Color.web("#fbff0025"));
        }

        pane.getChildren().addAll(rect, label);

        return pane;
    }

    // Eventos simulados
    //private static void verLote(MouseEvent event) {
        //aqui deve chamar a tela do lote/medicamento correspondente
        //System.out.println("Retângulo clicado!");
    //}

    private static EventHandler<MouseEvent> criarMudaCor(boolean ehVermelha) {
    return event -> {
        Rectangle rect = (Rectangle) event.getSource();
        if(ehVermelha){
            rect.setFill(Color.web("#FF000012"));
        } else {
            rect.setFill(Color.web("#fbff0010"));
        }
    };
}

    private static EventHandler<MouseEvent> criarVoltaCorOriginal(boolean ehVermelha) {
        return event -> {
            Rectangle rect = (Rectangle) event.getSource();
            if(ehVermelha){
                rect.setFill(Color.web("#FF000020"));
            } else {
                rect.setFill(Color.web("#fbff0025"));
            }
        };
    }
}