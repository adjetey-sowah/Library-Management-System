package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.TransactionDAO;
import com.juls.firstapp.librarymanagementsystem.model.enums.TransactionType;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.LinkedList;

public class TransactionRepository implements TransactionDAO {


    private Connection connection;
    private PreparedStatement preparedStatement;

    public TransactionRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
    }

    @Override
    public boolean createTransaction(Transaction transaction) {
        String sql = "{call addTransaction(?,?,?,?,?,?)}";

        var date = Instant.now();
        try (CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,transaction.getTransactionId());
            callableStatement.setLong(2,transaction.getResource());
            callableStatement.setLong(3,transaction.getPatronId());
            callableStatement.setDate(4, (Date) Date.from(date));
            callableStatement.setDate(5, Date.valueOf(String.valueOf(transaction.getDueDate())));
            callableStatement.setDate(6,null);

            return callableStatement.executeUpdate() > 0;

        }
        catch(SQLException e){
            throw new RuntimeException("Cannot create transaction: "+e.getMessage());
        }

    }

    @Override
    public boolean deleteTransaction(Long id) {
        return false;
    }

    @Override
    public ArrayDeque<Transaction> findAllTransactions() {
        ArrayDeque<Transaction> transactions = new ArrayDeque<>();

        String sql = "{call getAllTransactions()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();


        }
    }

    @Override
    public ArrayDeque<Transaction> findTransactionByRange(LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public LinkedList<LibraryResource> getAllBorrowedResource() {
        return null;
    }

    @Override
    public ArrayDeque<Transaction> findTransactionByType(TransactionType type) {
        return null;
    }

    @Override
    public ArrayDeque<Transaction> findTransactionByPatron(Long patronId) {
        return null;
    }

    @Override
    public ArrayDeque<Transaction> findTransactionByPatron(String name) {
        return null;
    }
}
