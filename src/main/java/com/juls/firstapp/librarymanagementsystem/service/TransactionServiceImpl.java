package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.TransactionRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.users.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Objects;

public class TransactionServiceImpl implements  TransactionService{

    private final UserRepository userRepository;
    final ResourceRepository resourceRepository;
    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl() throws Exception {
        userRepository = new UserRepository();
        resourceRepository = new ResourceRepository();
        transactionRepository = new TransactionRepository();
    }

    public TransactionServiceImpl(DatabaseConfig databaseConfig) throws Exception {
        userRepository = new UserRepository(databaseConfig);
        resourceRepository = new ResourceRepository(databaseConfig);
        transactionRepository = new TransactionRepository(databaseConfig);
    }



    @Override
    public boolean borrowResource(String patronName, String resourceName, LocalDate dueDate) throws Exception {
        Long resourceId = this.resourceRepository.getResourceByTitle(resourceName).getResourceId();
        Long patronId = 0L;
        for (User user : userRepository.getAllUsers()){
            if (user.getName().equalsIgnoreCase(patronName)){
                patronId = user.getUserId();
                break;
            }
        }

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
    public boolean returnBook(Long transactionId) throws Exception {

        boolean isReturned = false;
        TransactionDTO transaction = new TransactionDTO();

        for(TransactionDTO transactionDTO : getAllTransactions()){
            if (Objects.equals(transactionDTO.getTransactionId(), transactionId)){
                transaction = transactionDTO;
            }
        }

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


        System.out.println("All transactions");
        implement.getAllTransactions().forEach(System.out::println);

    }

}

