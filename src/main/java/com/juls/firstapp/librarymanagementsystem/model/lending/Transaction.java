package com.juls.firstapp.librarymanagementsystem.model.lending;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private Long transactionId;
    private Long patronId;
    private Long resourceId;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnedDate;
    private double fine;

    public Transaction(Long transactionId,
                       Long patronId,
                       LocalDateTime returnedDate,
                       Long resourceId,
                       LocalDateTime borrowedDate,
                       LocalDateTime dueDate,
                       double fine) {
        this.transactionId = transactionId;
        this.patronId = patronId;
        this.returnedDate = returnedDate;
        this.resourceId = resourceId;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.fine = fine;
    }


    public Transaction(){

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

    public void setReturnedDate(LocalDateTime returnedDate){
        this.returnedDate = returnedDate;
    }

    public LocalDateTime getReturnedDate(){
        return returnedDate;
    }

    public Long getResource() {
        return resourceId;
    }

    public void setResource(Long resource) {
        this.resourceId = resource;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", patronId=" + patronId +
                ", returnedDate=" + returnedDate +
                ", resourceId=" + resourceId +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", fine=" + fine +
                '}';
    }
}
