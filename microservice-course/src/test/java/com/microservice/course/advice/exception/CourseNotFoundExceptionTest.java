package com.microservice.course.advice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseNotFoundExceptionTest {
    @Test
    void testDefaultConstructor() {
        CourseNotFoundException exception = new CourseNotFoundException();
        assertNull(exception.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String message = "Course not found";
        CourseNotFoundException exception = new CourseNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException();
        CourseNotFoundException exception = new CourseNotFoundException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testMessageAndCauseConstructor() {
        String message = "Course not found";
        Throwable cause = new RuntimeException();
        CourseNotFoundException exception = new CourseNotFoundException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testThrowsException() {
        assertThrows(CourseNotFoundException.class, () -> {
            throw new CourseNotFoundException();
        });
    }
}