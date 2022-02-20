package pt.isec.gestaodepinformatica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/LoginView.fxml")));

       // Scene scene = new Scene(root,400,500);
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle("Gestão do Dep. de Informática - Login");
        primaryStage.setScene(scene);


        scene.getStylesheets().add("/estilos.css");

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}