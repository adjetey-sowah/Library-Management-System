package com.juls.firstapp.librarymanagementsystem.unit.exception;

import com.juls.firstapp.librarymanagementsystem.util.exception.UserNotAddedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserNotAddedExceptionTest {

    @Test
    void testDefaultConstructor() {
        UserNotAddedException exception = new UserNotAddedException();
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithMessage() {
        String message = "User could not be added";
        UserNotAddedException exception = new UserNotAddedException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "User could not be added";
        Throwable cause = new Throwable("Database error");
        UserNotAddedException exception = new UserNotAddedException(message, cause);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithCause() {
        Throwable cause = new Throwable("Database error");
        UserNotAddedException exception = new UserNotAddedException(cause);
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithAllParameters() {
        String message = "User could not be added";
        Throwable cause = new Throwable("Database error");
        boolean enableSuppression = true;
        boolean writableStackTrace = false;

        UserNotAddedException exception = new UserNotAddedException(message, cause, enableSuppression, writableStackTrace);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception.getSuppressed().length == 0); // Suppression flag is true, but we don't add suppressed exceptions
    }
}

