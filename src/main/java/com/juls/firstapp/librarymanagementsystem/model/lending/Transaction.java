package com.juls.firstapp.librarymanagementsystem.model.lending;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private Long transactionId;
    private Long patronId;
    private Long resourceId;
    private LocalDateTime borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private double fine;

    public Transaction(Long transactionId,
                       Long patronId,
                       LocalDate returnedDate,
                       Long resourceId,
                       LocalDateTime borrowedDate,
                       LocalDate dueDate,
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

    public void setReturnedDate(LocalDate returnedDate){
        this.returnedDate = returnedDate;
    }

    public LocalDate getReturnedDate(){
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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
