package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.TransactionDAO;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class TransactionRepository implements TransactionDAO {


    private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);
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
            log.info("Transaction Logged successfully");
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

    public ArrayDeque<TransactionDTO> getTransactionByUserAndResource(Long userId, Long resourceId) throws SQLException {
        String sql = "{call getTransactionByUserAndResource(?,?)";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,userId);
            callableStatement.setLong(2,resourceId);
            ResultSet resultSet = callableStatement.executeQuery();
            TransactionDTO transactionDTO = new TransactionDTO();
            ArrayDeque<TransactionDTO> transactionList = new ArrayDeque<>();
            while (resultSet.next()){
                transactionList.addFirst(mappers.mapToTransaction(resultSet));
            }
            return transactionList;
        }
    }

    public ArrayDeque <TransactionDTO> getTransactionByUser(Long userId){
        String sql = "{call getTransactionsByUserId(?)}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,userId);
            ResultSet resultSet = callableStatement.executeQuery();
            ArrayDeque<TransactionDTO> transactionList = new ArrayDeque<>();

            while (resultSet.next()){
                transactionList.addFirst(mappers.mapToTransaction(resultSet));
            }
            return transactionList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TransactionDTO getTransactionByTransactionIdAndUserId(Long transactionId, Long userId) throws SQLException {
        String sql = "{call getTransactionById(?,?)}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setLong(1,transactionId);
            callableStatement.setLong(2,userId);
            ResultSet resultSet = callableStatement.executeQuery();
            TransactionDTO transaction = new TransactionDTO();

            while (resultSet.next()){
                transaction = mappers.mapToTransaction(resultSet);
            }

            return transaction;
        }
    }

    public boolean updateTransaction(TransactionDTO transaction){
        Long transaction_id = transaction.getTransactionId();
        String sql = "UPDATE Transaction SET returned_date = ? WHERE transaction_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(2,transaction_id);
            preparedStatement.setDate(1,Date.valueOf(LocalDate.now()));
            log.info("I have been logged into the database {}", Date.valueOf(LocalDate.now()));
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

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()){
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