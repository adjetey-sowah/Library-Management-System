package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.TransactionRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class TransactionServiceImpl implements  TransactionService{

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl() throws Exception {
        userRepository = new UserRepository();
        resourceRepository = new ResourceRepository();
        transactionRepository = new TransactionRepository();
    }


    @Override
    public boolean borrowResource(Long resourceId, Long patronId, LocalDate dueDate) throws Exception {
            LibraryResource resource = resourceRepository.findResourceById(resourceId);
            if(resource.getResourceStatus().equals(ResourceStatus.BORROWED)){
                System.out.println("Resource is not available");
                return false;
            }

            Transaction transaction = new Transaction();
            transaction.setResource(resourceId);
            transaction.setPatronId(patronId);
            transaction.setFine(0.0);
            transaction.setDueDate(dueDate);
            resource.setResourceStatus(ResourceStatus.BORROWED);
            resourceRepository.updateLibraryResource(resource);
            return transactionRepository.createTransaction(transaction);
    }

    @Override
    public void reserveBook(Long resourceId, Long patronId) {

    }

    public ArrayDeque<TransactionDTO> getAllTransactions() throws SQLException {
        return transactionRepository.findAllTransactions();
    }

    @Override
    public void checkReservationList() {

    }

    @Override
    public boolean returnBook(LocalDate searchString) throws Exception {

        boolean isReturned = false;
        TransactionDTO transaction = transactionRepository.getTransactionByDate(searchString);
        Long transactionId =  transaction.getTransactionId();
        if(transactionRepository.updateTransaction(transactionId)){
            LibraryResource resource = resourceRepository.getResourceByTitle(transaction.getResourceName());
            resource.setResourceStatus(ResourceStatus.AVAILABLE);
            resourceRepository.updateLibraryResource(resource);
            isReturned = true;
        }
        return isReturned;
    }

    @Override
    public LinkedList<LibraryResource> borrowedResources() throws Exception {
        return transactionRepository.getAllBorrowedResource();
    }

    @Override
    public LinkedList<LibraryResource> borrowedResourceByPatron(String search) {
        return null;
    }

    public ArrayDeque<TransactionDTO> getTransactionByPatron(String search) throws SQLException {
        return this.transactionRepository.findTransactionByPatron(search);
    }

    public static void main(String[] args) throws Exception {
        TransactionServiceImpl implement = new TransactionServiceImpl();
            implement.borrowResource(35L,1L,LocalDate.of(2024,12,1));
            implement.borrowResource(29L,6L,LocalDate.of(2024,12,5));

        System.out.println("Transaction Added successfully");

        System.out.println("All transactions");
        implement.getAllTransactions().forEach(System.out::println);

        System.out.println("All borrowed resources");
        implement.borrowedResources().forEach(System.out::println);
        System.out.println();
        System.out.println();

        implement.getTransactionByPatron("moses").forEach(System.out::println);

        if(implement.returnBook(LocalDate.of(2024,11,17))){
            System.out.println("Item return successfully");
        }


        implement.borrowedResources().forEach(System.out::println);
        System.out.println();
        System.out.println();

        implement.getTransactionByPatron("moses").forEach(System.out::println);
        }

}

