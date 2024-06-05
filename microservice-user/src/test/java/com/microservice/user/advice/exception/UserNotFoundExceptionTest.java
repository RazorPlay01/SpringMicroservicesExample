package com.microservice.user.advice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {
    @Test
    void testDefaultConstructor() {
        UserNotFoundException exception = new UserNotFoundException();
        assertNull(exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException();
        UserNotFoundException exception = new UserNotFoundException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Course not found";
        Throwable cause = new RuntimeException();
        UserNotFoundException exception = new UserNotFoundException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testThrowsException() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException();
        });
    }
}
