package pt.isec.gestaodepinformatica.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class SobreController {
    @FXML
    private Button RegressarId;
    @FXML
    void onRegressar(ActionEvent event) {

            Stage primarystage = (Stage) RegressarId.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/LoginView.fxml")));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/estilos.css");
                scene.setFill(Color.WHITE);
                primarystage.setScene(scene);
                primarystage.centerOnScreen();


            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}
