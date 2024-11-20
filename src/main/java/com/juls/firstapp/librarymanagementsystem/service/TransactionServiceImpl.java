package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ReservationRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.TransactionRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class TransactionServiceImpl implements  TransactionService{

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final TransactionRepository transactionRepository;
    private final ReservationRepository reservationRepository;


    public TransactionServiceImpl() throws Exception {
        userRepository = new UserRepository();
        resourceRepository = new ResourceRepository();
        transactionRepository = new TransactionRepository();
        reservationRepository = new ReservationRepository();
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
    public Queue<ReservationDTO> checkReservationList() {
        return reservationRepository.getAllReservations();
    }

    @Override
    public boolean returnBook(Long transactionId, Long userId) throws Exception {

        boolean isReturned = false;
        TransactionDTO transactionDTO = transactionRepository.getTransactionByTransactionIdAndUserId(transactionId,userId);
        if(transactionRepository.updateTransaction(transactionDTO)){
            LibraryResource resource = resourceRepository.getResourceByTitle(transactionDTO.getResourceName());
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
        LinkedList<LibraryResource> borrowedList = new LinkedList<>();

        try {
            for (LibraryResource libraryResource : borrowedResources()){

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayDeque<TransactionDTO> getTransactionByPatron(String search) throws SQLException {
        return this.transactionRepository.findTransactionByPatron(search);
    }

    public static void main(String[] args) throws Exception {
        TransactionServiceImpl implement = new TransactionServiceImpl();



        implement.checkReservationList().forEach(System.out::println);

}
}

