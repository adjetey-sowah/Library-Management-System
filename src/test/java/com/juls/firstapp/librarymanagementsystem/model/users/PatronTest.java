package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class PatronTest {

    @InjectMocks
    private Patron patron;

    private final String TEST_NAME = "Alice Smith";
    private final String TEST_EMAIL = "alice.smith@example.com";
    private final String TEST_PHONE = "555123456";
    private final MembershipType TEST_MEMBERSHIP_TYPE = MembershipType.STUDENT;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patron = new Patron(TEST_NAME, TEST_MEMBERSHIP_TYPE, TEST_EMAIL, TEST_PHONE);
    }

    @Test
    void testPatronConstructor() {
        assertNotNull(patron);
        assertEquals(TEST_NAME, patron.getName());
        assertEquals(TEST_EMAIL, patron.getEmail());
        assertEquals(TEST_PHONE, patron.getPhoneNum());
        assertEquals(TEST_MEMBERSHIP_TYPE, patron.getMembershipType());
        assertEquals(UserRole.PATRON, patron.getRole());
        assertNotNull(patron.getBorrowedResources());
        assertTrue(patron.getBorrowedResources().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        patron.setMembershipType(MembershipType.RESEARCHER);
        assertEquals(MembershipType.RESEARCHER, patron.getMembershipType());
    }

    @Test
    void testBorrowAndReturnResource() {
        LibraryResource resource = new Book();
        patron.borrowResource(resource);

        assertEquals(1, patron.getBorrowedResources().size());
        assertTrue(patron.getBorrowedResources().contains(resource));

        patron.returnResource(resource);
        assertTrue(patron.getBorrowedResources().isEmpty());
    }
}