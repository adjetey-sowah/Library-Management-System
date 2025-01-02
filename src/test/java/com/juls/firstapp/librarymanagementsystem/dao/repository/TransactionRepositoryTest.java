package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.util.helper.Mappers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class TransactionRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Mappers mappers;

    @Mock
    private CallableStatement callableStatement;

    @Mock ResourceRepository resourceRepository;

    @InjectMocks
    private TransactionRepository transactionRepository;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
//
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(callableStatement.executeQuery()).thenReturn(resultSet);

        transactionRepository = spy(new TransactionRepository(databaseConfig));
    }

    @Test
    void createTransaction_Success() throws SQLException {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setPatronId(1L);
        transaction.setResource(1L);
        transaction.setBorrowedDate(LocalDate.now().atStartOfDay());
        transaction.setDueDate(LocalDate.now().plusDays(7));
        transaction.setFine(0.0);
        String sql = "INSERT INTO transaction(resource_id,user_id,borrowed_date,due_date,fine) VALUES (?,?,?,?,?)";

        when(mockConnection.prepareCall(sql)).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenReturn(1);  // Simulate successful insertion

        // Act
        boolean result = transactionRepository.createTransaction(transaction);

        // Assert
        assertTrue(result);
        verify(callableStatement).setLong(1, transaction.getResource());
        verify(callableStatement).setLong(2, transaction.getPatronId());
        verify(callableStatement).setDate(3, Date.valueOf(LocalDate.now()));
        verify(callableStatement).setDate(4, Date.valueOf(transaction.getDueDate()));
        verify(callableStatement).setDouble(5, transaction.getFine());
    }

    @Test
    void createTransaction_Failure_SQLException() throws SQLException {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setPatronId(1L);
        transaction.setResource(1L);
        transaction.setBorrowedDate(LocalDate.now().atStartOfDay());
        transaction.setDueDate(LocalDate.now().plusDays(7));
        transaction.setFine(0.0);
        String sql = "INSERT INTO transaction(resource_id,user_id,borrowed_date,due_date,fine) VALUES (?,?,?,?,?)";

        when(mockConnection.prepareCall(sql)).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenThrow(new SQLException("Failed to insert transaction"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> transactionRepository.createTransaction(transaction));


    }

    @Test
    void createTransaction_Failure_RuntimeException() throws SQLException {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setPatronId(1L);
        transaction.setResource(1L);
        transaction.setBorrowedDate(LocalDate.now().atStartOfDay());
        transaction.setDueDate(LocalDate.now().plusDays(7));
        transaction.setFine(0.0);
        String sql = "INSERT INTO transaction(resource_id,user_id,borrowed_date,due_date,fine) VALUES (?,?,?,?,?)";

        when(mockConnection.prepareCall(sql)).thenReturn(callableStatement);
        when(callableStatement.executeUpdate()).thenThrow(new SQLException("Cannot create transaction"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionRepository.createTransaction(transaction);
        });
        assertTrue(exception.getMessage().contains("Cannot create transaction"));
    }

    @Test
    void getTransactionByDate_Success() throws SQLException {
        // Arrange
        LocalDate date = LocalDate.now();
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setBorrowedDate(date);

        when(transactionRepository.findAllTransactions()).thenReturn(new ArrayDeque<>(java.util.List.of(transactionDTO)));

        // Act
        TransactionDTO result = transactionRepository.getTransactionByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(date, result.getBorrowedDate());
    }



    @Test
    void getTransactionByDate_Failure_SQLException() throws SQLException {
        // Arrange
        LocalDate date = LocalDate.now();
        when(transactionRepository.findAllTransactions()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            transactionRepository.getTransactionByDate(date);
        });
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void updateTransaction_Success() throws SQLException {
        // Arrange
        Long transactionId = 1L;
        String sql = "UPDATE Transaction SET returned_date = ? WHERE transaction_id = ?";

        when(mockConnection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful update

        // Act
        boolean result = transactionRepository.updateTransaction(transactionId);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setLong(2, transactionId);
        verify(preparedStatement).setDate(1, Date.valueOf(LocalDate.now()));
    }

    @Test
    void updateTransaction_Failure_SQLException() throws SQLException {
        // Arrange
        Long transactionId = 1L;
        String sql = "UPDATE Transaction SET returned_date = ? WHERE transaction_id = ?";

        when(mockConnection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Failed to update transaction"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionRepository.updateTransaction(transactionId);
        });
        assertEquals("Transaction not updated: Failed to update transaction", exception.getMessage());
    }

    @Test
    void updateTransaction_Failure_RuntimeException() throws SQLException {
        // Arrange
        Long transactionId = 1L;
        String sql = "UPDATE Transaction SET returned_date = ? WHERE transaction_id = ?";

        when(mockConnection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionRepository.updateTransaction(transactionId);
        });
        assertTrue(exception.getMessage().contains("Transaction not updated"));
    }

    @Test
    void findAllTransactions_Success() throws SQLException {
        // Arrange
        TransactionDTO transactionDTO = new TransactionDTO();
        when(transactionRepository.findAllTransactions()).thenReturn(new ArrayDeque<>(java.util.List.of(transactionDTO)));

        // Act
        ArrayDeque<TransactionDTO> result = transactionRepository.findAllTransactions();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(transactionDTO, result.peekFirst());
    }

    @Test
    void findAllTransactions_Failure_SQLException() throws SQLException {
        // Arrange
        when(transactionRepository.findAllTransactions()).thenThrow(new SQLException("Error fetching transactions"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            transactionRepository.findAllTransactions();
        });
        assertEquals("Error fetching transactions", exception.getMessage());
    }

    @Test
    void getAllBorrowedResource_Success() throws Exception {
        // Arrange
        LinkedList<LibraryResource> resources = new LinkedList<>();
        Book mockBook = new Book("Test Resource");
        mockBook.setResourceStatus(ResourceStatus.BORROWED);
        resources.add(mockBook);

        doReturn(resources).when(transactionRepository).getAllBorrowedResource();
        // Act
        LinkedList<LibraryResource> result = transactionRepository.getAllBorrowedResource();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(ResourceStatus.BORROWED, result.getFirst().getResourceStatus());
    }

    @Test
    void getAllBorrowedResource_Failure_Exception() throws Exception {
        // Arrange

        doThrow(new Exception("Error retrieving resources")).when(transactionRepository).getAllBorrowedResource();

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            transactionRepository.getAllBorrowedResource();
        });
        assertEquals("Error retrieving resources", exception.getMessage());
    }

    @Test
    void findTransactionByPatron_Success() throws SQLException {
        // Arrange
        String search = "John";
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setPatronName("John Doe");
        transactionDTO.setPhone("123456789");

;
        doReturn(new ArrayDeque<>(List.of(transactionDTO))).when(transactionRepository).findAllTransactions();

        // Act
        ArrayDeque<TransactionDTO> result = transactionRepository.findTransactionByPatron(search);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("John Doe", result.peekFirst().getPatronName());
    }

    @Test
    void findTransactionByPatron_Failure_SQLException() throws SQLException {
        // Arrange
        String search = "John";
        when(transactionRepository.findAllTransactions()).thenThrow(new SQLException("Error fetching transactions"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            transactionRepository.findTransactionByPatron(search);
        });
        assertEquals("Error fetching transactions", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Doe"})
    void findTransactionByPatron_Parameterized_Success(String search) throws SQLException {
        // Arrange
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setPatronName("John Doe");
        transactionDTO.setPhone("123456789");

        when(transactionRepository.findAllTransactions()).thenReturn(new ArrayDeque<>(List.of(transactionDTO)));
        doReturn(new ArrayDeque<>(List.of(transactionDTO))).when(transactionRepository).findAllTransactions();

        // Act
        ArrayDeque<TransactionDTO> result = transactionRepository.findTransactionByPatron(search);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.peekFirst().getPatronName().toLowerCase().contains(search.toLowerCase()));
    }



    @Test
    void findTransactionByRange_Failure_SQLException() throws SQLException {
        // Arrange
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();
        String sql = "{call findTransactionRange(?,?)}";

        // Simulate an SQLException
        when(mockConnection.prepareCall(sql)).thenThrow(new SQLException("Database error"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            transactionRepository.findTransactionByRange(from, to);
        });
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void findTransactionByRange_Failure_SQLException2() throws SQLException {
        // Arrange
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();
        String sql = "{call findTransactionRange(?,?)}";

        // Create mock callable statement that throws SQLException during execution
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(mockConnection.prepareCall(sql)).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenThrow(new SQLException("Database query error"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> {
            transactionRepository.findTransactionByRange(from, to);
        });
        assertTrue(exception.getMessage().contains("Database query error"));
    }



}
