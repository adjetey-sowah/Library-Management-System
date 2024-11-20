package com.juls.firstapp.librarymanagementsystem.service;


import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public interface TransactionService  {

    boolean borrowResource(Long resourceId, Long patronId, LocalDate dueDate) throws Exception;


    void reserveBook(Long resourceId, Long patronId);
    Queue<ReservationDTO> checkReservationList();
    boolean returnBook(Long userId, Long resourceId) throws Exception;
    LinkedList<LibraryResource> borrowedResources() throws Exception;
    LinkedList<LibraryResource> borrowedResourceByPatron(String phone);
}
