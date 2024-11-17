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
    public ArrayDeque<TransactionDTO> findAllTransactions() throws SQLException {
        ArrayDeque<TransactionDTO> transactions = new ArrayDeque<>();

        String sql = "{call getAllTransactions()}";

        try(CallableStatement callableStatement = connection.prepareCall(sql)){
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()){
                transactions.add(mappers.mapToTransaction(resultSet));
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
    public ArrayDeque<TransactionDTO> findTransactionByPatron(String phone) throws SQLException {
        ArrayDeque<TransactionDTO> transactionList = new ArrayDeque<>();
        for (TransactionDTO transaction : findAllTransactions()){
            if(transaction.getPhone().equalsIgnoreCase(phone)){
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }

    @Override
    public ArrayDeque<TransactionDTO> findTransactionByPatronName(String name) throws SQLException {
        ArrayDeque<TransactionDTO> transactionList = new ArrayDeque<>();
        for (TransactionDTO transaction : findAllTransactions()){
            if(transaction.getPhone().equalsIgnoreCase(name)){
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }
}
