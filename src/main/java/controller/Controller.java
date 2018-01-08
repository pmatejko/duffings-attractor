package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Duffing;
import model.Point2D;


public class Controller extends Application {
    private final int size = 500;
    private final Color black = new Color(0, 0, 0, 1.0);
    private final Color white = new Color(1, 1, 1, 1.0);
    private boolean draw = false;

    private Duffing duffing = new Duffing();

    private Stage primaryStage;
    private Parent splitPane;

    @FXML private TextField aInput;
    @FXML private TextField bInput;
    @FXML private TextField omegaInput;
    @FXML private Button startButton;
    @FXML private Canvas fractalCanvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/view.fxml"));
        splitPane = loader.load();

        Scene scene = new Scene(splitPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void refreshCanvas() {
        PixelWriter pixelWriter = fractalCanvas.getGraphicsContext2D().getPixelWriter();

        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                pixelWriter.setColor(i, j, black);
            }
        }
    }

    @FXML
    public void onStart() {
        draw = false;
        refreshCanvas();
        System.out.println("refreshed");

        try {
            double a = Double.valueOf(aInput.getCharacters().toString());
            double b = Double.valueOf(bInput.getCharacters().toString());
            double omega = Double.valueOf(omegaInput.getCharacters().toString());

            duffing.setA(a);
            duffing.setB(b);
            duffing.setOmega(omega);

            draw = true;
            System.out.println("starting");

            new AnimationTimer() {
                PixelWriter pixelWriter = fractalCanvas.getGraphicsContext2D().getPixelWriter();

                @Override
                public void handle(long now) {
                    Point2D point = duffing.getCurrentPoint();
                    double x = point.getX() * 100.0 + (size / 2);
                    double y = point.getY() * 100.0 + (size / 2);

                    System.out.println("(" + x + ", " + y + ")");

                    pixelWriter.setColor((int) x, (int) y, white);
                    duffing.iterate();
                }
            }.start();
        } catch (NumberFormatException e) {
            System.err.println("Incorrect number inserted");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
