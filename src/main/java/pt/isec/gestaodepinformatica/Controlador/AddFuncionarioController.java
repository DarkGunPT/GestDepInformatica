package pt.isec.gestaodepinformatica.Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.gestaodepinformatica.BaseDados.BaseDados;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddFuncionarioController{

    BaseDados conDB;
    Connection con;
    Statement statement;


    @FXML
    private TextField idNomeFuncionario;

    @FXML
    private TextField idUsernameFuncionario;

    @FXML
    private ChoiceBox idGeneroFuncionario;

    @FXML
    private TextField idMoradaFuncinario;

    @FXML
    private TextField idEmailFuncionario;

    @FXML
    private TextField idSalarioFuncionario;

    @FXML
    private TextField idTelefoneFuncionario;

    @FXML
    private PasswordField idPasswordFuncionario;

    @FXML
    private ChoiceBox idContratoFuncionario;

    @FXML
    private DatePicker idDataFuncionario;

    @FXML
    private TextField idCargoFuncionario;



    @FXML
    void onRegressarClick(ActionEvent event) {

        Stage primarystage = (Stage) idNomeFuncionario.getScene().getWindow();

        //Abrir a página do Admin
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/AdminView.fxml")));
            Scene scene = new Scene(root, 950, 550);
            scene.getStylesheets().add("/estilos.css");
            scene.setFill(Color.WHITE);

            primarystage.setScene(scene);
            primarystage.setTitle("Gestão do Dep. de Informática - Administrador");
            primarystage.centerOnScreen();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onConfirmarClick(ActionEvent event) throws SQLException{


        Stage primarystage = (Stage) idNomeFuncionario.getScene().getWindow();



        conDB = new BaseDados();
        con = conDB.getDbConnection();
        statement = con.createStatement();
        Alert alert1 = new Alert(Alert.AlertType.WARNING);
        alert1.setTitle("WARNING");
        if(idUsernameFuncionario.getText().equals("")){
            alert1.setContentText("Complete o username do funcionário!");
            alert1.showAndWait();
            return;
        }
        if(idPasswordFuncionario.getText().equals("")){
            alert1.setContentText("Complete a password do funcionário!");
            alert1.showAndWait();
            return;
        }
        if(idCargoFuncionario.getText().equals("")){
            alert1.setContentText("Complete o cargo do funcionário!");
            alert1.showAndWait();
            return;
        }
        if(idSalarioFuncionario.getText().equals("")){
            alert1.setContentText("Complete o salario do funcionário!");
            alert1.showAndWait();
            return;
        }
        String contrato = " ";
        try {
            contrato = idContratoFuncionario.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            alert1.setContentText("Selecione o tipo de contrato do funcionário!");
            alert1.showAndWait();
            return;
        }
        float salario = 0;
        try {
            salario = Float.parseFloat(idSalarioFuncionario.getText());
        } catch (NumberFormatException e) {
            alert1.setContentText("Salário Introduziodo Inválido!");
            alert1.showAndWait();
            return;
        }
        String password = idPasswordFuncionario.getText();
        String username = idUsernameFuncionario.getText();
        String cargo = idCargoFuncionario.getText();
        String nome = idNomeFuncionario.getText();
        String genero;
        try {
            genero = idGeneroFuncionario.getSelectionModel().getSelectedItem().toString();
        } catch (NullPointerException e) {
            genero=" ";
        }
        String morada;
        if(idMoradaFuncinario.getText().equals("")){
            morada = " ";
        }else{
            morada = idMoradaFuncinario.getText();
        }

        String email = idEmailFuncionario.getText();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(email.equals("")){
            email = " ";
        }
        else if(!matcher.matches()){
            alert1.setContentText("Email introduzido inválido!");
            alert1.showAndWait();
            return;
        }

        String telefone;
        if(idTelefoneFuncionario.getText().equals("")){
            telefone = " ";
        }else if (!idTelefoneFuncionario.getText().matches("^[0-9]+$")){
            alert1.setContentText("Telefone - Deve inserir apenas valores numéricos!");
            alert1.showAndWait();
            return;
        } else{
            telefone = idTelefoneFuncionario.getText();
        }

        //idContratoFuncionario.getSelectionModel().getSelectedItem().toString();

        LocalDate data ;
        String sql;
        if(idDataFuncionario.getValue()==null){
            sql = "INSERT INTO Utilizadores (Username, Password, NomeCompleto, Genero, Contrato, Servico, Morada, Email, Telefone, Salario, isAdmin) VALUES ('"+username+"', '"+password+"', '"+nome+"', '"+genero+"', '"+contrato+"', '"+cargo+"', '"+morada+"', '"+email+"', '"+telefone+"', "+salario+", 0);";

        }else{
            data = idDataFuncionario.getValue();
            sql = "INSERT INTO Utilizadores (Username, Password, NomeCompleto, DataNascimento, Genero, Contrato, Servico, Morada, Email, Telefone, Salario, isAdmin) VALUES ('"+username+"', '"+password+"', '"+nome+"', '"+data+"', '"+genero+"', '"+contrato+"', '"+cargo+"', '"+morada+"', '"+email+"', '"+telefone+"', "+salario+", 0);";

        }
        ButtonType confirmar = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Deseja mesmo adicionar o funcionário?",
                confirmar,
                cancelar);
        alert.setTitle("Adicionar funcionário");

        Optional<ButtonType> result = alert.showAndWait();
       // String sql = "INSERT INTO Utilizadores (Username, Password, NomeCompleto, DataNascimento, Genero, Contrato, Servico, Morada, Email, Telefone, Salario, isAdmin) VALUES ('"+username+"', '"+password+"', '"+nome+"', '"+data+"', '"+genero+"', '"+contrato+"', '"+cargo+"', '"+morada+"', '"+email+"', '"+telefone+"', "+salario+", 0);";
        if(result.orElse(cancelar) == confirmar){
            try {
                statement.executeUpdate(sql);
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Sucesso");
                alert2.setContentText("Funcionário adicionado com sucesso!");
                alert2.showAndWait();


                Parent root;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/AdminView.fxml")));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/estilos.css");
                scene.setFill(Color.WHITE);
                primarystage.setScene(scene);
                primarystage.centerOnScreen();




            } catch (SQLException | IOException throwables) {
                //throwables.printStackTrace();
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erro");
                alert2.setContentText("Não foi possível adicionar o funcionário!");
                alert2.showAndWait();
            }
        }
    }
}
