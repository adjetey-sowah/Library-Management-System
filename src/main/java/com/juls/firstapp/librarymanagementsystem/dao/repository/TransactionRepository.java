package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.TransactionDAO;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class TransactionRepository implements TransactionDAO {


    private final Connection connection;
    private final Mappers mappers;
    private final ResourceRepository resourceRepository;

    public TransactionRepository() throws SQLException, ClassNotFoundException {
        this.connection = new DatabaseConfig().getConnection();
        this.resourceRepository = new ResourceRepository();
        this.mappers = new Mappers();
    }

    @Override
    public boolean createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction(resource_id,user_id,borrowed_date,due_date,fine) VALUES (?,?,?,?,?)";


        try (CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,transaction.getResource());
            callableStatement.setLong(2,transaction.getPatronId());
            callableStatement.setDate(3,Date.valueOf(LocalDate.now()));
            callableStatement.setDate(4, Date.valueOf(String.valueOf(transaction.getDueDate())));
            callableStatement.setDouble(5,transaction.getFine());

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

    public TransactionDTO getTransactionByDate(LocalDate transactionDate) throws SQLException {
        TransactionDTO transactionDTO = new TransactionDTO();
        for (TransactionDTO transactions : findAllTransactions()){
            if (transactions.getBorrowedDate().equals(transactionDate)){
                transactionDTO = transactions;
            }
        }
        return transactionDTO;
    }

    public TransactionDTO getTransactionById(Long transactionId) throws SQLException {

        String sql = "{call getTransactionById(?)}";

        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            callableStatement.setLong(1, transactionId);
            ResultSet resultSet = callableStatement.executeQuery();
            TransactionDTO transactionDTO = new TransactionDTO();
            while (resultSet.next()) {
                transactionDTO = mappers.mapToTransaction(resultSet);
            }
            return transactionDTO;
        }
    }


    public boolean updateTransaction(Long transaction_id){
        String sql = "UPDATE Transaction SET returned_date = ? WHERE transaction_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(2,transaction_id);
            preparedStatement.setDate(1,Date.valueOf(LocalDate.now()));
            return preparedStatement.executeUpdate() > 0;
        }
        catch (Exception e){
            throw new RuntimeException("Transaction not updated: "+e.getMessage());
        }
    }

    @Override
    public ArrayDeque<TransactionDTO> findAllTransactions() throws SQLException {
        ArrayDeque<TransactionDTO> transactions = new ArrayDeque<>();
        String sql = "{call getAllTransactions()}";
        try (CallableStatement callableStatement = connection.prepareCall(sql)) {
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                transactions.addFirst(mappers.mapToTransaction(resultSet));
            }
        }
        return transactions;
    }

    @Override
    public ArrayDeque<TransactionDTO> findTransactionByRange(LocalDate from, LocalDate to) throws SQLException {
        String sql ="{call findTransactionRange(?,?)}";
        ArrayDeque<TransactionDTO> transactionRange = new ArrayDeque<>();
        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                transactionRange.add(mappers.mapToTransaction(resultSet));
            }
            return transactionRange;

        }

    }

    @Override
    public LinkedList<LibraryResource> getAllBorrowedResource() throws Exception {
        LinkedList<LibraryResource> libraryResources = this.resourceRepository.findAllResource();
        LinkedList<LibraryResource> borrowedResource = new LinkedList<>();
        for (LibraryResource resource : libraryResources){
            if(resource.getResourceStatus().equals(ResourceStatus.BORROWED)){
                borrowedResource.add(resource);
            }
        }
        return borrowedResource;
    }

    @Override
    public ArrayDeque<TransactionDTO> findTransactionByPatron(String search) throws SQLException {
        ArrayDeque<TransactionDTO> transactionList = new ArrayDeque<>();
        for (TransactionDTO transaction : findAllTransactions()){
            if(transaction.getPhone().equalsIgnoreCase(search) ||
                    transaction.getPatronName().toLowerCase().contains(search.toLowerCase())){
                transactionList.addFirst(transaction);
            }
        }
        return transactionList;
    }

}