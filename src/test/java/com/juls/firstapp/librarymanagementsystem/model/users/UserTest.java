package com.juls.firstapp.librarymanagementsystem.model.users;

import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @InjectMocks
    private User user;

    private final String TEST_NAME = "Test User";
    private final String TEST_EMAIL = "test.user@example.com";
    private final String TEST_PHONE = "123456789";
    private final UserRole TEST_ROLE = UserRole.PATRON;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(TEST_NAME, TEST_EMAIL, TEST_PHONE, TEST_ROLE);
    }

    @Test
    void testUserConstructor() {
        assertNotNull(user);
        assertEquals(TEST_NAME, user.getName());
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_PHONE, user.getPhoneNum());
        assertEquals(TEST_ROLE, user.getRole());
    }

    @Test
    void testSettersAndGetters() {
        user.setName("New Name");
        user.setEmail("new.email@example.com");
        user.setPhoneNum("987654321");
        user.setRole(UserRole.LIBRARIAN);
        user.setUserId(1L);

        assertEquals("New Name", user.getName());
        assertEquals("new.email@example.com", user.getEmail());
        assertEquals("987654321", user.getPhoneNum());
        assertEquals(UserRole.LIBRARIAN, user.getRole());
        assertEquals(1L, user.getUserId());
    }

    @Test
    void testToString() {
        String expected = "User{" +
                "userId=null" +
                ", name='" + TEST_NAME + '\'' +
                ", email='" + TEST_EMAIL + '\'' +
                ", phoneNum='" + TEST_PHONE + '\'' +
                ", Role='" + TEST_ROLE.toString() + '\'' +
                '}';
        assertEquals(expected, user.toString());
    }
}
