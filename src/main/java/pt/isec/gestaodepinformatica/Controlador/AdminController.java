package pt.isec.gestaodepinformatica.Controlador;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.gestaodepinformatica.BaseDados.BaseDados;
import pt.isec.gestaodepinformatica.Modelo.FuncionarioModelo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminController implements Initializable {


    private static String username;
    BaseDados conDB;
    Connection con;
    Statement statement;
    ResultSet queryResultado;



    @FXML
    private TableView<FuncionarioModelo> tabelaFuncionarios;

    @FXML
    private TableColumn<FuncionarioModelo, String> usernameCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> contratoCol;

    @FXML
    private TableColumn<FuncionarioModelo, Date> dataCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> emailCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> generoCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> moradaCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> nomeCol;

    @FXML
    private TextField pesquisaTextfield;

    @FXML
    private TableColumn<FuncionarioModelo, Double> salarioCol;

    @FXML
    private TableColumn<FuncionarioModelo, String> servicoCol;


    @FXML
    private TableColumn<FuncionarioModelo, String> telefoneCol;

    @FXML
    private Button removerBtn;

    @FXML
    private Button editarBtn;


    ObservableList<FuncionarioModelo> pesquisarFuncObservableList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conDB = new BaseDados();
        con = conDB.getDbConnection();


        Thread thread = new Thread(() -> {

            while (true) {
                if (tabelaFuncionarios.getSelectionModel().getSelectedItem() != null) {
                    removerBtn.setDisable(false);
                    editarBtn.setDisable(false);
                } else {
                    removerBtn.setDisable(true);
                    editarBtn.setDisable(true);
                }
            }
        });

        thread.start();
        updateList();


        //Pesquisar
            FilteredList<FuncionarioModelo> filteredData = new FilteredList<>(pesquisarFuncObservableList, b -> true);

            pesquisaTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(productSearchModel -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();
                    if (productSearchModel.getUsername().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if (productSearchModel.getNomeCompleto().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getContrato().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (productSearchModel.getServico().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else {
                        return false;
                    }
                });

                SortedList<FuncionarioModelo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tabelaFuncionarios.comparatorProperty());

                tabelaFuncionarios.setItems(sortedData);

            });
    }


    @FXML
    void onAdicionarFuncionarioClick(ActionEvent event) {


        //Abrir a página de Adicionar Funcionário
        Stage primarystage = (Stage) tabelaFuncionarios.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/AdicionarFuncionarioView.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/estilos.css");
            scene.setFill(Color.WHITE);
            primarystage.setScene(scene);
            primarystage.setTitle("Gestão do Dep. de Informática - Adicionar Funcionário");
            primarystage.centerOnScreen();

        } catch (Exception e) {
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Erro ao adicionar um Funcionário!");
            alert.showAndWait();
        }
    }
    public void onEditarFuncionarioClick (ActionEvent actionEvent) {
        username = tabelaFuncionarios.getSelectionModel().getSelectedItem().getUsername();
        //Abrir a página de Adicionar Funcionário
        Stage primarystage = (Stage) tabelaFuncionarios.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pt/isec/gestaodepinformatica/EditarFuncionarioView.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/estilos.css");
            scene.setFill(Color.WHITE);
            primarystage.setScene(scene);
            primarystage.setTitle("Gestão do Dep. de Informática - Adicionar Funcionário");
            primarystage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Erro ao Editar um Funcionário!");
            alert.showAndWait();
        }
    }

    public static String getUsername(){
        return username;
    }



    @FXML
    void onEliminarFuncionarioClick(ActionEvent event) {


        ButtonType confirmar = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Deseja mesmo remover o funcionário '"
                + tabelaFuncionarios.getSelectionModel().getSelectedItem().getNomeCompleto() + "' ?",
                confirmar,
                cancelar);

        alert.setTitle("Remover um funcionário");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(cancelar) == confirmar) {
            try {
                String sql = "DELETE FROM Utilizadores WHERE Username='" + tabelaFuncionarios.getSelectionModel().getSelectedItem().getUsername() + "';";
                statement = con.createStatement();
                statement.executeUpdate(sql);
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Sucesso");
                alert1.setContentText("Funcionario " + tabelaFuncionarios.getSelectionModel().getSelectedItem().getNomeCompleto() + " eliminado com sucesso!");
                alert1.showAndWait();

                Stage primarystage = (Stage) tabelaFuncionarios.getScene().getWindow();
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
                alert2.setContentText("Não foi possível eliminar o funcionario!");
                alert2.showAndWait();
            }

        }
    }

    public void updateList() {


        String sqlQuery = "SELECT Username, NomeCompleto, Servico,Contrato,Telefone,DataNascimento,Morada,Email,Genero,Salario FROM Utilizadores WHERE isAdmin = 0";

        try {
            statement = con.createStatement();
            queryResultado = statement.executeQuery(sqlQuery);
            while ((queryResultado.next())) {
                String queryUsername = queryResultado.getString("Username");
                String queryNomeCompleto = queryResultado.getString("NomeCompleto");
                String queryServico = queryResultado.getString("Servico");
                String queryContrato = queryResultado.getString("Contrato");
                String queryTelefone = queryResultado.getString("Telefone");
                Date queryDataNascimento = queryResultado.getDate("DataNascimento");
                String queryMorada = queryResultado.getString("Morada");
                String queryEMail = queryResultado.getString("Email");
                String queryGenero = queryResultado.getString("Genero");
                Double querySalario = queryResultado.getDouble("Salario");

                pesquisarFuncObservableList.add(new FuncionarioModelo(queryUsername,queryNomeCompleto, queryServico, queryContrato, queryTelefone, queryDataNascimento, queryMorada, queryEMail, queryGenero, querySalario));
            }

            usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
            nomeCol.setCellValueFactory(new PropertyValueFactory<>("NomeCompleto"));
            servicoCol.setCellValueFactory(new PropertyValueFactory<>("Servico"));
            contratoCol.setCellValueFactory(new PropertyValueFactory<>("Contrato"));
            telefoneCol.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
            dataCol.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));
            moradaCol.setCellValueFactory(new PropertyValueFactory<>("Morada"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
            generoCol.setCellValueFactory(new PropertyValueFactory<>("Genero"));
            salarioCol.setCellValueFactory(new PropertyValueFactory<>("Salario"));

        } catch (SQLException e) {
            Logger.getLogger(FuncionarioModelo.class.getName()).log(Level.SEVERE, null, e);
        }
        tabelaFuncionarios.setItems(pesquisarFuncObservableList);

    }



    public void onSairClick (ActionEvent actionEvent) {
        Stage primarystage = (Stage) editarBtn.getScene().getWindow();
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

