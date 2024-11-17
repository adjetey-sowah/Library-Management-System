package com.juls.firstapp.librarymanagementsystem.model.lending;

import com.juls.firstapp.librarymanagementsystem.model.enums.TransactionType;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.time.LocalDateTime;

public class Transaction {
    private static Long count = 10L;
    private Long transactionId;
    private Long patronId;
    private TransactionType transactionType;
    private LibraryResource resource;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private double fine;

    public Transaction(Long transactionId,
                       Long patronId,
                       TransactionType transactionType,
                       LibraryResource resource,
                       LocalDateTime borrowedDate,
                       LocalDateTime dueDate,
                       double fine) {
        this.transactionId = transactionId;
        this.patronId = patronId;
        this.transactionType = transactionType;
        this.resource = resource;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.fine = fine;
    }


    public Transaction(){

    }

    protected Long generateId(){
        return this.transactionId += count++;

    }



    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    public void setTransactionType(TransactionType type){
        this.transactionType = type;
    }

    public TransactionType getTransactionType(){
        return transactionType;
    }

    public long getResource() {
        return resource;
    }

    public void setResource(LibraryResource resource) {
        this.resource = resource;
    }

    public LocalDateTime getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDateTime borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
