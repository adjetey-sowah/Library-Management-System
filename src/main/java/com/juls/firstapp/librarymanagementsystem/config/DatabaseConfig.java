package com.juls.firstapp.librarymanagementsystem.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class DatabaseConfig {

    private final Connection connection;

    public DatabaseConfig() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try{
            Dotenv dotenv = Dotenv.load();
           String dbUrl = dotenv.get("DB_URL");
           String dbUser = dotenv.get("DB_USER");
           String dbPassword = dotenv.get("DB_PASSWORD");

           this.connection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        }

        catch (Exception e){
            throw new RuntimeException(("Database Connection failed" + e.getMessage()));
        }
    }

    public Connection getConnection(){
        return this.connection;
    }



    public void trialTest() throws SQLException {
        Statement statement= this.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES");

        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        DatabaseConfig connector = new DatabaseConfig();
        connector.trialTest();


    }
}
