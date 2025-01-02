package com.juls.firstapp.librarymanagementsystem.service;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.TransactionRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Transaction;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TransactionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Connection mockConnection;

    @Mock
    private CallableStatement callableStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private LibraryResource testResource;
    private User testUser;
    private Transaction testTransaction;
    private TransactionDTO testTransactionDTO;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
        mockConnection = spy(Connection.class);
        resourceRepository = new ResourceRepository(databaseConfig);
        userRepository = spy(new UserRepository(databaseConfig));
        transactionRepository = new TransactionRepository(databaseConfig);

        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareCall(anyString())).thenReturn(callableStatement);
        when(callableStatement.executeQuery()).thenReturn(resultSet);

        transactionService = spy(new TransactionServiceImpl(databaseConfig));

        testResource = new Book();
        testResource.setResourceId(1L);
        testResource.setTitle("Test Book");
        testResource.setResourceStatus(ResourceStatus.AVAILABLE);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setName("Test User");

        testTransaction = new Transaction();
        testTransaction.setTransactionId(1L);
        testTransaction.setResource(1L);
        testTransaction.setPatronId(1L);

        testTransactionDTO = new TransactionDTO();
        testTransactionDTO.setTransactionId(1L);
        testTransactionDTO.setResourceName("Test Book");
    }


}