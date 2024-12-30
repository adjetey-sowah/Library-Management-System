package com.juls.firstapp.librarymanagementsystem.dao.repository;

import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private CallableStatement mockCallableStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Spy
    private PreparedStatement mockPreparedStatement;


    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareCall("{call get_all_users()}")).thenReturn(mockCallableStatement);
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);


        userRepository = new UserRepository(databaseConfig);
    }

    @Test
    void testInsertUserSuccessful() throws SQLException {
        // Mock the generated keys
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNum("123456789");
        user.setRole(UserRole.PATRON);

        int userId = userRepository.insertUser(user);

        assertEquals(1, userId);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testInsertUserFailure() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNum("123456789");
        user.setRole(UserRole.PATRON);

        assertThrows(SQLException.class, () -> {
            userRepository.insertUser(user);
        });
    }

    // Tests for insertPatron
    @Test
    void insertPatron_Success() throws SQLException {
        // Arrange
        Patron patron = new Patron();
        patron.setName("Test Patron");
        patron.setEmail("patron@test.com");
        patron.setPhoneNum("1234567890");
        patron.setRole(UserRole.PATRON);
        patron.setMembershipType(MembershipType.STUDENT);

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Second prepareStatement call for patron table
        PreparedStatement mockPatronPS = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(contains("INSERT INTO patron")))
                .thenReturn(mockPatronPS);

        // Act
        assertDoesNotThrow(() -> userRepository.insertPatron(patron));

        // Assert
        verify(mockPatronPS).setInt(1, 1);
        verify(mockPatronPS).setString(2, MembershipType.STUDENT.name());
        verify(mockPatronPS).executeUpdate();
    }



    @Test
    void insertPatron_ThrowsRuntimeException() throws SQLException {
        // Arrange
        Patron patron = new Patron();
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        when(mockConnection.prepareStatement(contains("INSERT INTO patron")))
                .thenThrow(new SQLException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userRepository.insertPatron(patron));
    }

    @Test
    void updateUser_Patron_Success() throws SQLException {
        // Arrange
        Patron patron = new Patron();
        patron.setUserId(1L);
        patron.setName("Updated Patron");
        patron.setEmail("patron@test.com");
        patron.setPhoneNum("9999999999");
        patron.setRole(UserRole.PATRON);
        patron.setMembershipType(MembershipType.RESEARCHER);

        PreparedStatement mockPatronPS = mock(PreparedStatement.class);
        PreparedStatement mockUserPS = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(contains("UPDATE patron")))
                .thenReturn(mockPatronPS);
        when(mockConnection.prepareStatement(contains("UPDATE Users")))
                .thenReturn(mockUserPS);
        when(mockUserPS.executeUpdate()).thenReturn(2);

        // Act
        boolean result = userRepository.updateUser(patron);

        // Assert
        verify(mockPatronPS).setString(1, MembershipType.RESEARCHER.toString());
        verify(mockPatronPS).setLong(2, 1L);
        verify(mockUserPS).executeUpdate();
        assertTrue(result);
    }

    // Tests for getLibrarianPassword


    // Tests for deleteUser
    @Test
    void deleteUser_Success() throws SQLException {
        // Arrange
        Long userId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = userRepository.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(mockPreparedStatement).setLong(1, userId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void deleteUser_Failure() throws SQLException {
        // Arrange
        Long userId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        // Act
        boolean result = userRepository.deleteUser(userId);

        // Assert
        assertFalse(result);
    }


    @Test
    void getLibrarianPassword_ThrowsRuntimeException() throws SQLException {
        // Arrange
        Long userId = 1L;
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userRepository.getLibrarianPassword(userId));
    }



    @Test
    void getLibrarianPassword_UserNotFound() throws SQLException {
        // Arrange
        Long userId = 999L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act & Assert
        String result = userRepository.getLibrarianPassword(userId);
        assertEquals("", result);
    }


    // Tests for updateUser
    @Test
    void updateUser_Librarian_Success() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setUserId(1L);
        librarian.setName("Updated Librarian");
        librarian.setEmail("updated@test.com");
        librarian.setPhoneNum("9999999999");
        librarian.setRole(UserRole.LIBRARIAN);
        librarian.setPassword("newpassword");

        PreparedStatement mockLibrarianPS = mock(PreparedStatement.class);
        PreparedStatement mockUserPS = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(contains("UPDATE librarian")))
                .thenReturn(mockLibrarianPS);
        when(mockConnection.prepareStatement(contains("UPDATE Users")))
                .thenReturn(mockUserPS);
        when(mockUserPS.executeUpdate()).thenReturn(2);

        // Act
        boolean result = userRepository.updateUser(librarian);

        // Assert
        verify(mockLibrarianPS).setString(eq(1), anyString()); // Encoded password
        verify(mockLibrarianPS).setLong(2, 1L);
        verify(mockUserPS).executeUpdate();
        assertTrue(result);
    }



    // Tests for insertLibrarian
    @Test
    void insertLibrarian_Success() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setName("Test Librarian");
        librarian.setEmail("librarian@test.com");
        librarian.setPhoneNum("1234567890");
        librarian.setRole(UserRole.LIBRARIAN);
        librarian.setPassword("password123");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        PreparedStatement mockLibrarianPS = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(contains("INSERT INTO librarian")))
                .thenReturn(mockLibrarianPS);

        // Act
        assertDoesNotThrow(() -> userRepository.insertLibrarian(librarian));

        // Assert
        verify(mockLibrarianPS).setInt(1, 1);
        verify(mockLibrarianPS).setString(eq(2), anyString()); // Password is encoded
        verify(mockLibrarianPS).executeUpdate();
    }

    @Test
    void insertLibrarian_ThrowsRuntimeException() throws SQLException {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setName("Test Librarian");
        librarian.setEmail("librarian@test.com");
        librarian.setPhoneNum("1234567890");
        librarian.setRole(UserRole.LIBRARIAN);
        librarian.setPassword("password123");

        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        PreparedStatement mockLibrarianPS = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(contains("INSERT INTO librarian"))).thenReturn(mockLibrarianPS);

        // Simulate failure on executeUpdate
        when(mockLibrarianPS.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepository.insertLibrarian(librarian));
        assertEquals("java.sql.SQLException: Database error", exception.getMessage());

        // Verify interactions
        verify(mockLibrarianPS).setInt(1, 1);
        verify(mockLibrarianPS).setString(eq(2), anyString()); // Password is encoded
        verify(mockLibrarianPS).executeUpdate();
    }


    @Test
    void testUserRepositoryConstructor() throws SQLException, ClassNotFoundException {
        // Check if the connection in UserRepository is set to the mockConnection
        assertNotNull(userRepository); // Ensure UserRepository is instantiated
        assertSame(mockConnection, userRepository.getConnection(), "The connection should be the same as the mocked one.");
    }


    @Test
    void testGetAllUsers_Successful() throws SQLException {
        // Prepare mock result set to simulate the database response
        when(mockConnection.prepareCall("{call get_all_users()}")).thenReturn(mockCallableStatement);
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock resultSet to simulate user data being returned
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);  // Two users returned
        when(mockResultSet.getLong("user_id")).thenReturn(1L).thenReturn(2L);
        when(mockResultSet.getString("name")).thenReturn("User One").thenReturn("User Two");
        when(mockResultSet.getString("email")).thenReturn("user1@example.com").thenReturn("user2@example.com");
        when(mockResultSet.getString("phone")).thenReturn("123456789").thenReturn("987654321");
        when(mockResultSet.getString("role")).thenReturn("PATRON").thenReturn("LIBRARIAN");

        // Call the method
        LinkedList<User> users = userRepository.getAllUsers();

        // Verify the results
        assertNotNull(users);
        assertEquals(2, users.size());  // We expect two users
        assertEquals("User One", users.get(0).getName());
        assertEquals("User Two", users.get(1).getName());

        verify(mockCallableStatement, times(1)).executeQuery();
    }

    @Test
    void testGetAllUsers_EmptyResultSet() throws SQLException {
        // Prepare mock result set to simulate the database response
        when(mockConnection.prepareCall("{call get_all_users()}")).thenReturn(mockCallableStatement);
        when(mockCallableStatement.executeQuery()).thenReturn(mockResultSet);

        // Simulate an empty result set (no users returned)
        when(mockResultSet.next()).thenReturn(false);

        // Call the method
        LinkedList<User> users = userRepository.getAllUsers();

        // Verify the results
        assertNotNull(users);
        assertTrue(users.isEmpty());  // No users should be returned

        verify(mockCallableStatement, times(1)).executeQuery();
    }

    @Test
    void testGetAllUsers_SQLException() throws SQLException {
        // Simulate a SQLException when executing the query
        when(mockCallableStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Call the method and verify that it throws a SQLException
        assertThrows(SQLException.class, () -> {
            userRepository.getAllUsers();
        });

        verify(mockCallableStatement, times(1)).executeQuery();
    }




    @ParameterizedTest
    @ValueSource(strings = {"valid@example.com", "invalid@example.com"})
    void testFindUserByEmail(String email) throws SQLException {
        // Mock the connection and statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        User user = userRepository.getUserbyEmail(email);

        assertNotNull(user);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    void testFindUserByEmailSQLException() throws SQLException {
        // Simulate SQLException
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> {
            userRepository.getUserbyEmail("test@example.com");
        });
    }


    @Test
    void getUserbyEmail_Failure() throws SQLException {
        // Arrange
        String email = "nonexistent@test.com";

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        SQLException exception = assertThrows(SQLException.class, () -> userRepository.getUserbyEmail(email));
        assertEquals("Database error", exception.getMessage());

        // Verify interactions
        verify(mockPreparedStatement).setString(1, email);
        verify(mockPreparedStatement).executeQuery();
    }


    // Parameterized test for membership types in patron updates
    @ParameterizedTest
    @EnumSource(MembershipType.class)
    void updateUser_Patron_WithDifferentMembershipTypes(MembershipType membershipType) throws SQLException {
        // Arrange
        Patron patron = new Patron();
        patron.setUserId(1L);
        patron.setMembershipType(membershipType);

        PreparedStatement mockPatronPS = mock(PreparedStatement.class);
        PreparedStatement mockUserPS = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(contains("UPDATE patron")))
                .thenReturn(mockPatronPS);
        when(mockConnection.prepareStatement(contains("UPDATE Users")))
                .thenReturn(mockUserPS);
        when(mockUserPS.executeUpdate()).thenReturn(2);

        // Act
        boolean result = userRepository.updateUser(patron);

        // Assert
        verify(mockPatronPS).setString(1, membershipType.toString());
        assertTrue(result);
    }


    // Tests for getLibrarianPassword


    @Test
    void findUserById_WhenUserDoesNotExist() throws SQLException {
        // Arrange
        Long userId = 999L;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Act
        Optional<User> result = userRepository.findUserById(userId);

        // Assert
        assertTrue(result.isPresent()); // As per current implementation
        assertNull(result.get().getName()); // The user object will be empty
    }





    // Integration test for getAllUsers
}

