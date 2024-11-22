package com.juls.firstapp.librarymanagementsystem.service;


import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.time.LocalDate;
import java.util.LinkedList;

public interface TransactionService  {

    boolean borrowResource(String patronName, String resourceName, LocalDate dueDate) throws Exception;


    void reserveBook(Long resourceId, Long patronId);
    void checkReservationList();
    boolean returnBook(LocalDate search) throws Exception;
    LinkedList<LibraryResource> borrowedResources() throws Exception;
    LinkedList<LibraryResource> borrowedResourceByPatron(String phone);
}
