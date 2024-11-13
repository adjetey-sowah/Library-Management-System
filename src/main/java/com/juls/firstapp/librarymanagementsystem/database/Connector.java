package com.juls.firstapp.librarymanagementsystem.database;

import com.juls.firstapp.librarymanagementsystem.ConfigLoader;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Connector {



    private final Dotenv dotenv = Dotenv.load();
    private final Connection connection = DriverManager.getConnection(dotenv.get("DB_URL"),dotenv.get("DB_USER"),
            dotenv.get("DB_PASSWORD"));

    public Connector() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public void trialTest() throws SQLException {
        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES");

        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Connector connector = new Connector();
        connector.trialTest();


    }
}
