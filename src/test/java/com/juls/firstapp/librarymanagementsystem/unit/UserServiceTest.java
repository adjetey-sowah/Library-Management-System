package com.juls.firstapp.librarymanagementsystem.unit;



import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.service.UserService;
import com.juls.firstapp.librarymanagementsystem.util.exception.UserNotAddedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
}
