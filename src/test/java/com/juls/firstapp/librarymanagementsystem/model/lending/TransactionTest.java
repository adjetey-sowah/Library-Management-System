package com.juls.firstapp.librarymanagementsystem.model.lending;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(transaction);
        assertNull(transaction.getTransactionId());
        assertNull(transaction.getPatronId());
        assertNull(transaction.getResource());
        assertNull(transaction.getBorrowedDate());
        assertNull(transaction.getDueDate());
        assertNull(transaction.getReturnedDate());
        assertEquals(0.0, transaction.getFine());
    }

    @Test
    void testParameterizedConstructor() {
        Long transactionId = 1L;
        Long patronId = 2L;
        Long resourceId = 3L;
        LocalDateTime borrowedDate = LocalDateTime.of(2024, 12, 1, 10, 0);
        LocalDate dueDate = LocalDate.of(2024, 12, 15);
        LocalDate returnedDate = LocalDate.of(2024, 12, 10);
        double fine = 5.0;

        transaction = new Transaction(transactionId, patronId, returnedDate, resourceId, borrowedDate, dueDate, fine);

        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(patronId, transaction.getPatronId());
        assertEquals(resourceId, transaction.getResource());
        assertEquals(borrowedDate, transaction.getBorrowedDate());
        assertEquals(dueDate, transaction.getDueDate());
        assertEquals(returnedDate, transaction.getReturnedDate());
        assertEquals(fine, transaction.getFine());
    }

    @Test
    void testSetAndGetTransactionId() {
        Long transactionId = 100L;
        transaction.setTransactionId(transactionId);
        assertEquals(transactionId, transaction.getTransactionId());
    }

    @Test
    void testSetAndGetPatronId() {
        Long patronId = 1L;
        transaction.setPatronId(patronId);
        assertEquals(patronId, transaction.getPatronId());
    }

    @Test
    void testSetAndGetResource() {
        Long resourceId = 2L;
        transaction.setResource(resourceId);
        assertEquals(resourceId, transaction.getResource());
    }

    @Test
    void testSetAndGetBorrowedDate() {
        LocalDateTime borrowedDate = LocalDateTime.of(2024, 12, 1, 10, 0);
        transaction.setBorrowedDate(borrowedDate);
        assertEquals(borrowedDate, transaction.getBorrowedDate());
    }

    @Test
    void testSetAndGetDueDate() {
        LocalDate dueDate = LocalDate.of(2024, 12, 15);
        transaction.setDueDate(dueDate);
        assertEquals(dueDate, transaction.getDueDate());
    }

    @Test
    void testSetAndGetReturnedDate() {
        LocalDate returnedDate = LocalDate.of(2024, 12, 10);
        transaction.setReturnedDate(returnedDate);
        assertEquals(returnedDate, transaction.getReturnedDate());
    }

    @Test
    void testSetAndGetFine() {
        double fine = 10.0;
        transaction.setFine(fine);
        assertEquals(fine, transaction.getFine());
    }

    @Test
    void testToString() {
        Long transactionId = 1L;
        Long patronId = 2L;
        Long resourceId = 3L;
        LocalDateTime borrowedDate = LocalDateTime.of(2024, 12, 1, 10, 0);
        LocalDate dueDate = LocalDate.of(2024, 12, 15);
        LocalDate returnedDate = LocalDate.of(2024, 12, 10);
        double fine = 5.0;

        transaction = new Transaction(transactionId, patronId, returnedDate, resourceId, borrowedDate, dueDate, fine);

        String expected = "Transaction{" +
                "transactionId=" + transactionId +
                ", patronId=" + patronId +
                ", returnedDate=" + returnedDate +
                ", resourceId=" + resourceId +
                ", borrowedDate=" + borrowedDate +
                ", dueDate=" + dueDate +
                ", fine=" + fine +
                '}';
        assertTrue(transaction.toString().contains(transactionId.toString()));
    }

    @Test
    void testSetReturnedDateUsingMockito() {
        // Using Mockito to mock LocalDate class to simulate a returned date
        LocalDate mockDate = Mockito.mock(LocalDate.class);
        Mockito.when(mockDate.getYear()).thenReturn(2024);

        transaction.setReturnedDate(mockDate);

        assertNotNull(transaction.getReturnedDate());
        assertEquals(2024, transaction.getReturnedDate().getYear());
    }
}
