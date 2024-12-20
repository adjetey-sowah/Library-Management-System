package com.juls.firstapp.librarymanagementsystem.service;



import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import com.juls.firstapp.librarymanagementsystem.util.exception.UserNotAddedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    Patron patron = new Patron("Jane Doe", MembershipType.FACULTY, "janedoe@example.com", "password123");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    @DisplayName("Should add librarian successfully")
    void addLibrarian_Success() {
        // Arrange
        Librarian librarian = new Librarian("John", "Doe", "johndoe@example.com", "password123");
        when(userRepository.insertUser(librarian)).thenReturn(1);
        doNothing().when(userRepository).insertLibrarian(librarian);

        // Act
        Librarian result = (Librarian) userService.addLibrarian(librarian);

        // Assert
        assertNotNull(result);
        assertEquals(librarian, result);
        verify(userRepository).insertUser(librarian);
        verify(userRepository).insertLibrarian(librarian);
    }

    @Test
    @DisplayName("Should throw UserNotAddedException when insertUser fails")
    void addLibrarian_InsertUserFails() {
        // Arrange
        Librarian librarian = new Librarian("John", "Doe", "johndoe@example.com", "password123");
        when(userRepository.insertUser(librarian)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        UserNotAddedException exception = assertThrows(UserNotAddedException.class, () -> userService.addLibrarian(librarian));
        assertEquals("User was not added.Database error", exception.getMessage());
        verify(userRepository).insertUser(librarian);
        verify(userRepository, never()).insertLibrarian(any(Librarian.class));
    }

    @Test
    @DisplayName("Should throw UserNotAddedException when insertLibrarian fails")
    void addLibrarian_InsertLibrarianFails() {
        // Arrange
        Librarian librarian = new Librarian("John", "Doe", "johndoe@example.com", "password123");
        when(userRepository.insertUser(librarian)).thenReturn(1);
        doThrow(new RuntimeException("Database error")).when(userRepository).insertLibrarian(librarian);

        // Act & Assert
        UserNotAddedException exception = assertThrows(UserNotAddedException.class, () -> userService.addLibrarian(librarian));
        assertEquals("User was not added.Database error", exception.getMessage());
        verify(userRepository).insertUser(librarian);
        verify(userRepository).insertLibrarian(librarian);
    }


    @Test
    @DisplayName("Should add patron successfully")
    void testAddPatron() {
        // Arrange
        when(userRepository.insertUser(patron)).thenReturn(1);
        doNothing().when(userRepository).insertPatron(patron);

        // Act
        Patron result = (Patron) userService.addPatron(patron);

        // Assert
        assertNotNull(result);
        assertEquals(patron, result);
        verify(userRepository).insertUser(patron);
        verify(userRepository).insertPatron(patron);
    }

    @Test
    @DisplayName("Should throw UserNotAddedException when insertUser fails in addPatron")
    void addPatron_InsertUserFails() {
        // Arrange
        Patron patron = new Patron("Jane Doe", MembershipType.FACULTY, "janedoe@example.com", "password123");
        when(userRepository.insertUser(patron)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        UserNotAddedException exception = assertThrows(UserNotAddedException.class, () -> userService.addPatron(patron));
        assertEquals("Patron can not be added.\nDatabase error", exception.getMessage());
        verify(userRepository).insertUser(patron);
        verify(userRepository, never()).insertPatron(any(Patron.class));
    }

    @Test
    @DisplayName("Should throw UserNotAddedException when insertPatron fails in addPatron")
    void addPatron_InsertPatronFails() {
        // Arrange
        Patron patron = new Patron("Jane Doe", MembershipType.FACULTY, "janedoe@example.com", "password123");
        when(userRepository.insertUser(patron)).thenReturn(1);
        doThrow(new RuntimeException("Database error")).when(userRepository).insertPatron(patron);

        // Act & Assert
        UserNotAddedException exception = assertThrows(UserNotAddedException.class, () -> userService.addPatron(patron));
        assertEquals("Patron can not be added.\nDatabase error", exception.getMessage());
        verify(userRepository).insertUser(patron);
        verify(userRepository).insertPatron(patron);
    }

    @Test
    @DisplayName("Should delete user successfully")
    void deleteUser_Success() throws SQLException {
        // Arrange
        User user = new User("Jane Doe", "janedoe@example.com", "123456789", UserRole.PATRON);
        when(userRepository.deleteUser(user.getUserId())).thenReturn(true); // or whatever the method returns

        // Act
        userService.deletUser(user);

        // Assert
        verify(userRepository).deleteUser(user.getUserId());
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleteUser fails")
    void deleteUser_Failure() throws SQLException {
        // Arrange
        User user = new User("Jane Doe", "janedoe@example.com", "123456789", UserRole.PATRON);
        doThrow(new RuntimeException("Database error")).when(userRepository).deleteUser(user.getUserId());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deletUser(user));
        assertEquals("Could not delete user.\nDatabase error", exception.getMessage());
        verify(userRepository).deleteUser(user.getUserId());
    }


}
