package com.juls.firstapp.librarymanagementsystem.dao.interfaces;

import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.model.enums.TransactionType;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;

public interface TransactionDAO {

    boolean createTransaction(Transaction transaction);
    boolean deleteTransaction(Long id);
    ArrayDeque<TransactionDTO> findAllTransactions() throws SQLException;
    ArrayDeque<TransactionDTO> findTransactionByRange(LocalDate from, LocalDate to) throws SQLException;
    LinkedList<LibraryResource> getAllBorrowedResource() throws Exception;
    ArrayDeque<TransactionDTO> findTransactionByPatron(String phone) throws SQLException;
}
