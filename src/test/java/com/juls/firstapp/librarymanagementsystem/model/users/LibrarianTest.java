package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LibrarianTest {

    @InjectMocks
    private Librarian librarian;

    private final String TEST_NAME = "John Doe";
    private final String TEST_EMAIL = "john.doe@example.com";
    private final String TEST_PHONE = "123456789";
    private final String TEST_PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        librarian = new Librarian(TEST_NAME, TEST_EMAIL, TEST_PHONE, TEST_PASSWORD);
    }

    @Test
    void testLibrarianConstructor() {
        assertNotNull(librarian);
        assertEquals(TEST_NAME, librarian.getName());
        assertEquals(TEST_EMAIL, librarian.getEmail());
        assertEquals(TEST_PHONE, librarian.getPhoneNum());
        assertEquals(TEST_PASSWORD, librarian.getPassword());
        assertEquals(UserRole.LIBRARIAN, librarian.getRole());
    }

    @Test
    void testSettersAndGetters() {
        librarian.setPassword("newPassword");
        librarian.setName("Jane Doe");
        librarian.setEmail("jane.doe@example.com");
        librarian.setPhoneNum("987654321");

        assertEquals("newPassword", librarian.getPassword());
        assertEquals("Jane Doe", librarian.getName());
        assertEquals("jane.doe@example.com", librarian.getEmail());
        assertEquals("987654321", librarian.getPhoneNum());
    }
}