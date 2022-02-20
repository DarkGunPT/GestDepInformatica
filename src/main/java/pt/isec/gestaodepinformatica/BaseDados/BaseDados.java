package pt.isec.gestaodepinformatica.BaseDados;

import java.sql.*;
import java.util.Scanner;

public class BaseDados {

    private Connection dbConn;



    public Connection getDbConnection() {

       // String url = "jdbc:mysql://db4free.net:3306/gestao_db";
        String DATABASE_URL = "jdbc:mysql://db4free.net/gestao_db";
        String USERNAME = "andrelopes301";
        String PASSWORD = "12345678";

        try {
            dbConn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return dbConn;
    }




}
