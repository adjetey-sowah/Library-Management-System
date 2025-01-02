package com.juls.firstapp.librarymanagementsystem.config;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mysql.cj.jdbc.Driver;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

class DatabaseConfigTest {

    private DatabaseConfig databaseConfig;

    @BeforeEach
    void setUp() throws Exception {
        try (MockedStatic<Dotenv> mockedDotenv = mockStatic(Dotenv.class);
             MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {


            // Mock Dotenv behavior
            Dotenv mockDotenv = mock(Dotenv.class);
            mockedDotenv.when(Dotenv::load).thenReturn(mockDotenv);
            when(mockDotenv.get("DB_URL")).thenReturn("jdbc:mysql://localhost:3306/librarydb");
            when(mockDotenv.get("DB_USER")).thenReturn("root");
            when(mockDotenv.get("DB_PASSWORD")).thenReturn("password");




            // Mock DriverManager to return a mock connection
            Connection mockConnection = mock(Connection.class);
            mockedDriverManager.when(() -> DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/librarydb", "root", "password"))
                    .thenReturn(mockConnection);
            // Create DatabaseConfig instance
            databaseConfig = new DatabaseConfig();

            // Assert that the connection is established
            assertNotNull(databaseConfig.getConnection());
        }
    }

    @Test
    void testDatabaseConfig_ShouldEstablishConnection() throws Exception {
        // Mocked connection from setup should not be null
        assertNotNull(databaseConfig.getConnection());
    }

    @Test
    void testTrialTest_ShouldExecuteShowTables() throws Exception {
        // Mock the Connection and Statement
        Connection mockConnection = databaseConfig.getConnection();
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Configure mock behavior
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SHOW TABLES")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString(1)).thenReturn("mock_table");

        // Capture the output to verify the printed result
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run the trialTest method
        databaseConfig.trialTest();

        // Verify interactions
        verify(mockStatement).executeQuery("SHOW TABLES");
        verify(mockResultSet, times(2)).next();
        assertTrue(outContent.toString().contains("mock_table"));
    }


}
