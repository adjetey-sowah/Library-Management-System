package com.juls.firstapp.librarymanagementsystem.dao.interfaces;

import com.juls.firstapp.librarymanagementsystem.model.enums.TransactionType;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;

public interface TransactionDAO {

    boolean createTransaction(Transaction transaction);
    boolean deleteTransaction(Long id);
    ArrayDeque<Transaction> findAllTransactions();
    ArrayDeque<Transaction>  findTransactionByRange(LocalDate from, LocalDate to);
    LinkedList<LibraryResource> getAllBorrowedResource();
    ArrayDeque<Transaction> findTransactionByType(TransactionType type);
    ArrayDeque<Transaction> findTransactionByPatron(Long patronId);
    ArrayDeque<Transaction> findTransactionByPatron(String name);
}
