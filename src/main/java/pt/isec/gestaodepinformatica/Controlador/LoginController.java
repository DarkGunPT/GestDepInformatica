package pt.isec.gestaodepinformatica.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.gestaodepinformatica.BaseDados.BaseDados;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {


    private static String userName;
    @FXML
    private Button Btn_Login;

    @FXML
    private ImageView Logo;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;




    ResultSet rs = null;
    PreparedStatement pst = null;



    public static String getUsername(){
        return userName;
    }

    @FXML
    void onBtnLogin(ActionEvent event) throws IOException {

        Stage primarystage = (Stage) Btn_Login.getScene().getWindow();
        //primarystage.close();



        if(Username.getText().isEmpty()){
            Username.requestFocus();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Username vazio!");
            alert.setHeaderText(null);
            alert.setContentText("Tem que introduzir o seu username!");
            alert.showAndWait();
            return;
        }

        if(Password.getText().isEmpty()){
            Password.requestFocus();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password vazia!");
            alert.setHeaderText(null);
            alert.setContentText("Tem que introduzir a sua password!");
            alert.showAndWait();
            return;
        }


        BaseDados conDB = new BaseDados();
        Connection con = conDB.getDbConnection();
        String sql = "Select * from Utilizadores where username = ? and password = ? ";

        try {

            pst = con.prepareStatement(sql);
            userName = Username.getText();
            pst.setString(1,userName);
            pst.setString(2,Password.getText());

            rs = pst.executeQuery();

            if(rs.next()){
                Parent root;
                if(rs.getBoolean("isAdmin")){
                    //Abrir a página do Admin
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/AdminView.fxml")));
                    primarystage.setTitle("Gestão do Dep. de Informática - Administrador");

                }else{

                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/FuncionarioView.fxml")));
                    primarystage.setTitle("Gestão do Dep. de Informática - Funcionário");
                }
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/estilos.css");
                scene.setFill(Color.WHITE);
                primarystage.setScene(scene);

                primarystage.centerOnScreen();


            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Verifique se introduziu o seu username e password correctamente!");
                alert.showAndWait();
            }


        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Possível Erro de base de dados!");
            alert.showAndWait();
        }








    }

    @FXML
    void onSobreClick(ActionEvent event) {
        Stage primarystage = (Stage) Btn_Login.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/SobreView.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/estilos.css");
            scene.setFill(Color.WHITE);
            primarystage.setScene(scene);
            primarystage.setTitle("Sobre");
            primarystage.centerOnScreen();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}