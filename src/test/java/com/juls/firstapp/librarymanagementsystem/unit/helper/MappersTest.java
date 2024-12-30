package com.juls.firstapp.librarymanagementsystem.unit.helper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.juls.firstapp.librarymanagementsystem.dao.dto.ReservationDTO;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.model.enums.*;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

class MappersTest {

    private Mappers mappers;

    @Mock
    private ResultSet resultSet;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mappers = new Mappers();

    }

    // --- Unit Tests ---

    @Test
    void testMapToUser() throws SQLException {
        // Arrange
        when(resultSet.getLong("user_id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getString("phone")).thenReturn("123456789");
        when(resultSet.getString("role")).thenReturn("PATRON");

        // Act
        Optional<User> user = mappers.mapToUser(resultSet);

        // Assert
        assertTrue(user.isPresent());
        assertEquals(1L, user.get().getUserId());
        assertEquals("John Doe", user.get().getName());
        assertEquals("john.doe@example.com", user.get().getEmail());
        assertEquals("123456789", user.get().getPhoneNum());
        assertEquals(UserRole.PATRON, user.get().getRole());
    }

    @Test
    void testMapToBooks() throws SQLException {
        // Arrange
        when(resultSet.getLong("resource_id")).thenReturn(101L);
        when(resultSet.getString("title")).thenReturn("The Great Book");
        when(resultSet.getString("author")).thenReturn("John Author");
        when(resultSet.getString("isbn")).thenReturn("123-456-789");
        when(resultSet.getString("genre")).thenReturn("FICTION");
        when(resultSet.getString("status")).thenReturn("AVAILABLE");
        when(resultSet.getString("publication_date")).thenReturn("2023-01-01");

        // Act
        Book book = mappers.mapToBooks(resultSet);

        // Assert
        assertNotNull(book);
        assertEquals("The Great Book", book.getTitle());
        assertEquals("John Author", book.getAuthor());
        assertEquals("123-456-789", book.getIsbn());
        assertEquals(Genre.FICTION, book.getGenre());
        assertEquals(ResourceStatus.AVAILABLE, book.getResourceStatus());
        assertEquals(LocalDate.of(2023, 1, 1), book.getPublicationDate());
    }

    @Test
    void testMapToJournal() throws SQLException {
        // Arrange
        when(resultSet.getLong("journal_id")).thenReturn(202L);
        when(resultSet.getString("title")).thenReturn("Science Journal");
        when(resultSet.getString("issue_no")).thenReturn("5");
        when(resultSet.getString("frequency")).thenReturn("Monthly");
        when(resultSet.getString("status")).thenReturn("BORROWED");

        // Act
        Journal journal = mappers.mapToJournal(resultSet);

        // Assert
        assertNotNull(journal);
        assertEquals("Science Journal", journal.getTitle());
        assertEquals("5", journal.getIssueNumber());
        assertEquals("Monthly", journal.getFrequency());
        assertEquals(ResourceStatus.BORROWED, journal.getResourceStatus());
    }

    @Test
    void testMapToMedia() throws SQLException {
        // Arrange
        when(resultSet.getLong("media_id")).thenReturn(303L);
        when(resultSet.getString("title")).thenReturn("Movie Title");
        when(resultSet.getString("format")).thenReturn("DVD");
        when(resultSet.getString("status")).thenReturn("AVAILABLE");

        // Act
        Media media = mappers.mapToMedia(resultSet);

        // Assert
        assertNotNull(media);
        assertEquals("Movie Title", media.getTitle());
        assertEquals(MediaFormat.DVD, media.getFormat());
        assertEquals(ResourceStatus.AVAILABLE, media.getResourceStatus());
    }

    @Test
    void testMapToTransaction() throws SQLException {
        // Arrange
        when(resultSet.getLong("transaction_id")).thenReturn(1001L);
        when(resultSet.getString("title")).thenReturn("The Great Book");
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("phone")).thenReturn("123456789");
        when(resultSet.getDate("borrowed_date")).thenReturn(Date.valueOf("2023-01-01"));
        when(resultSet.getDate("due_date")).thenReturn(Date.valueOf("2023-02-01"));
        when(resultSet.getDate("returned_date")).thenReturn(Date.valueOf("2023-01-10"));
        when(resultSet.getDouble("fine")).thenReturn(0.0);

        // Act
        TransactionDTO transactionDTO = mappers.mapToTransaction(resultSet);

        // Assert
        assertNotNull(transactionDTO);
        assertEquals(1001L, transactionDTO.getTransactionId());
        assertEquals("The Great Book", transactionDTO.getResourceName());
        assertEquals("John Doe", transactionDTO.getPatronName());
        assertEquals("123456789", transactionDTO.getPhone());
        assertEquals(LocalDate.of(2023, 1, 1), transactionDTO.getBorrowedDate());
        assertEquals(LocalDate.of(2023, 2, 1), transactionDTO.getDueDate());
        assertEquals(LocalDate.of(2023, 1, 10), transactionDTO.getReturnedDate());
        assertEquals(0.0, transactionDTO.getFine());
    }

    @Test
    void testMapToReservation() throws Exception {
        // Arrange
        Reservations reservation = mock(Reservations.class);
        when(reservation.getReservationDate()).thenReturn(LocalDate.of(2023, 1, 1).atStartOfDay());
        when(reservation.getStatus()).thenReturn(ReservationStatus.PENDING);

        // Act
        ReservationDTO reservationDTO = mappers.maptoReservation(reservation);

        // Assert
        assertNotNull(reservationDTO);
        assertEquals("Hello", reservationDTO.getUserName());
        assertEquals("World", reservationDTO.getResourceName());
        assertEquals(LocalDateTime.of(2023, 1, 1,00,00), reservationDTO.getReservationDate());
        assertEquals(ReservationStatus.PENDING, reservationDTO.getStatus());
    }
}

