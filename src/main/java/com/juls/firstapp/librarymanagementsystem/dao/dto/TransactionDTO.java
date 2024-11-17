package com.juls.firstapp.librarymanagementsystem.dao.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {

    private static final Logger log = LoggerFactory.getLogger(TransactionDTO.class);
    private Long transactionId;
    private String resourceName;
    private String patronName;
    private String phone;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private double fine;

    public TransactionDTO(Long transactionId,
                          String resourceName,
                          String patronName,
                          String phone,
                          LocalDate borrowedDate,
                          LocalDate dueDate,
                          LocalDate returnedDate, double fine)
    {
        this.transactionId = transactionId;
        this.resourceName = resourceName;
        this.patronName = patronName;
        this.phone = phone;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.fine = fine;
    }

    public TransactionDTO() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionId=" + transactionId +
                ", resourceName='" + resourceName + '\'' +
                ", patronName='" + patronName + '\'' +
                ", phone='" + phone + '\'' +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                ", fine=" + fine +
                '}';
    }
}
