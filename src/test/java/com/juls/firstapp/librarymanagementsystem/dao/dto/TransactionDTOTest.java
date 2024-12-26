package com.juls.firstapp.librarymanagementsystem.dao.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDTOTest {

    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTO = new TransactionDTO();
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Long transactionId = 101L;
        String resourceName = "Effective Java";
        String patronName = "Alice Johnson";
        String phone = "123-456-7890";
        LocalDate borrowedDate = LocalDate.of(2024, 12, 1);
        LocalDate dueDate = LocalDate.of(2024, 12, 15);
        LocalDate returnedDate = LocalDate.of(2024, 12, 14);
        double fine = 0.0;

        // Act
        transactionDTO.setTransactionId(transactionId);
        transactionDTO.setResourceName(resourceName);
        transactionDTO.setPatronName(patronName);
        transactionDTO.setPhone(phone);
        transactionDTO.setBorrowedDate(borrowedDate);
        transactionDTO.setDueDate(dueDate);
        transactionDTO.setReturnedDate(returnedDate);
        transactionDTO.setFine(fine);

        // Assert
        assertEquals(transactionId, transactionDTO.getTransactionId());
        assertEquals(resourceName, transactionDTO.getResourceName());
        assertEquals(patronName, transactionDTO.getPatronName());
        assertEquals(phone, transactionDTO.getPhone());
        assertEquals(borrowedDate, transactionDTO.getBorrowedDate());
        assertEquals(dueDate, transactionDTO.getDueDate());
        assertEquals(returnedDate, transactionDTO.getReturnedDate());
        assertEquals(fine, transactionDTO.getFine());
    }

    @Test
    void testToString() {
        // Arrange
        transactionDTO.setTransactionId(102L);
        transactionDTO.setResourceName("Clean Code");
        transactionDTO.setPatronName("Bob Smith");
        transactionDTO.setPhone("987-654-3210");
        transactionDTO.setBorrowedDate(LocalDate.of(2024, 11, 20));
        transactionDTO.setDueDate(LocalDate.of(2024, 12, 4));
        transactionDTO.setReturnedDate(LocalDate.of(2024, 12, 3));
        transactionDTO.setFine(10.0);

        String expectedString = "TransactionDTO{" +
                "transactionId=102, resourceName='Clean Code', patronName='Bob Smith', phone='987-654-3210', borrowedDate=2024-11-20, dueDate=2024-12-04, returnedDate=2024-12-03, fine=10.0}";

        // Act
        String actualString = transactionDTO.toString();

        // Assert
        assertEquals(expectedString, actualString);
    }

    @Test
    void testWithMockito() {
        // Arrange
        TransactionDTO mockDTO = Mockito.mock(TransactionDTO.class);
        Mockito.when(mockDTO.getTransactionId()).thenReturn(103L);
        Mockito.when(mockDTO.getResourceName()).thenReturn("Refactoring");
        Mockito.when(mockDTO.getPatronName()).thenReturn("Carol Brown");
        Mockito.when(mockDTO.getPhone()).thenReturn("555-555-5555");
        Mockito.when(mockDTO.getBorrowedDate()).thenReturn(LocalDate.of(2024, 10, 1));
        Mockito.when(mockDTO.getDueDate()).thenReturn(LocalDate.of(2024, 10, 15));
        Mockito.when(mockDTO.getReturnedDate()).thenReturn(LocalDate.of(2024, 10, 16));
        Mockito.when(mockDTO.getFine()).thenReturn(5.0);

        // Act
        Long transactionId = mockDTO.getTransactionId();
        String resourceName = mockDTO.getResourceName();
        String patronName = mockDTO.getPatronName();
        String phone = mockDTO.getPhone();
        LocalDate borrowedDate = mockDTO.getBorrowedDate();
        LocalDate dueDate = mockDTO.getDueDate();
        LocalDate returnedDate = mockDTO.getReturnedDate();
        double fine = mockDTO.getFine();

        // Assert
        assertEquals(103L, transactionId);
        assertEquals("Refactoring", resourceName);
        assertEquals("Carol Brown", patronName);
        assertEquals("555-555-5555", phone);
        assertEquals(LocalDate.of(2024, 10, 1), borrowedDate);
        assertEquals(LocalDate.of(2024, 10, 15), dueDate);
        assertEquals(LocalDate.of(2024, 10, 16), returnedDate);
        assertEquals(5.0, fine);

        // Verify interactions with mock
        Mockito.verify(mockDTO).getTransactionId();
        Mockito.verify(mockDTO).getResourceName();
        Mockito.verify(mockDTO).getPatronName();
        Mockito.verify(mockDTO).getPhone();
        Mockito.verify(mockDTO).getBorrowedDate();
        Mockito.verify(mockDTO).getDueDate();
        Mockito.verify(mockDTO).getReturnedDate();
        Mockito.verify(mockDTO).getFine();
    }
}
