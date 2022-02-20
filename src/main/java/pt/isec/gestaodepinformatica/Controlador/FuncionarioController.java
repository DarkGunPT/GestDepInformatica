package pt.isec.gestaodepinformatica.Controlador;


    import javafx.event.ActionEvent;
import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.paint.Color;
    import javafx.stage.Stage;
    import pt.isec.gestaodepinformatica.BaseDados.BaseDados;

    import java.net.URL;
    import java.sql.Connection;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.time.LocalDate;
    import java.util.Objects;
    import java.util.ResourceBundle;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

public class FuncionarioController implements Initializable {

    BaseDados conDB;
    Connection con;
    Statement statement;
    ResultSet rs;
    String username;

        @FXML
        private AnchorPane FuncionarioController;

        @FXML
        private DatePicker idData;

        @FXML
        private TextField idEmail;

        @FXML
        private ChoiceBox idGenero;

        @FXML
        private TextField idMorada;

        @FXML
        private TextField idNome;

        @FXML
        private PasswordField idPassword;

        @FXML
        private TextField idSalario;

        @FXML
        private TextField idTelefone;

        @FXML
        private TextField idUsername;

        @FXML
        private TextField idCargoFuncionario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conDB = new BaseDados();

        con = conDB.getDbConnection();
        username = LoginController.getUsername();
        //System.out.println(username);
        String sqlQuery = "SELECT * FROM Utilizadores WHERE Username = '"+username+"';";
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(sqlQuery);
            rs.next();
            idNome.setText(rs.getString("NomeCompleto"));
            idUsername.setText(username);
            idPassword.setText(rs.getString("Password"));
            idEmail.setText(rs.getString("Email"));
            idMorada.setText(rs.getString("Morada"));
            idGenero.setValue(rs.getString("Genero"));
            idSalario.setText(String.valueOf(rs.getFloat("Salario")));
            idTelefone.setText(rs.getString("Telefone"));
            idCargoFuncionario.setText(rs.getString("Servico"));
            if(rs.getDate("DataNascimento")==null){
                idData.setValue(LocalDate.of(1900,10,05));//1143
            }else{
                idData.setValue(rs.getDate("DataNascimento").toLocalDate());
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }






        @FXML
        void onAplicar(ActionEvent event) {

            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("WARNING");
            String email = idEmail.getText();
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

            String telefone = idTelefone.getText();
            if(idTelefone.getText().equals("")){
                telefone = " ";
            }else if (!idTelefone.getText().matches("^[0-9]+$")){
                alert1.setContentText("Telefone - Deve inserir apenas valores numéricos!");
                alert1.showAndWait();
                return;
            } else{
                telefone = idTelefone.getText();
            }

            String sqlQuery = "UPDATE Utilizadores SET NomeCompleto = '"+idNome.getText()+"', Password = '"+idPassword.getText()+"'," +
                    " Telefone = '"+telefone+"', DataNascimento = '"+idData.getValue()+"', Morada = '"+idMorada.getText()+"', " +
                "Email = '"+email+"' WHERE Username = '"+username+"';";

            try {
                statement = con.createStatement();
                statement.executeUpdate(sqlQuery);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Edit Success");
                alert.setHeaderText(null);
                alert.setContentText("Dados alterados com sucesso");
                alert.showAndWait();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edit Error");
                alert.setHeaderText(null);
                alert.setContentText("Erro a alterar Dados do Funcionário");
                alert.showAndWait();
            }

        }
        @FXML
        void onRegressar(ActionEvent event) {
            Stage primarystage = (Stage) FuncionarioController.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/LoginView.fxml")));
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
