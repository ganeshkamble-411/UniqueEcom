package Ecom.ModelDTO;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "/api/test",
                HttpStatus.BAD_REQUEST,
                "Invalid request",
                now
        );

        assertEquals("/api/test", errorResponse.getApiPath());
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getErrorCode());
        assertEquals("Invalid request", errorResponse.getErrorMessage());
        assertEquals(now, errorResponse.getErrorTime());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        LocalDateTime now = LocalDateTime.now();

        errorResponse.setApiPath("/api/test");
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage("Resource not found");
        errorResponse.setErrorTime(now);

        assertEquals("/api/test", errorResponse.getApiPath());
        assertEquals(HttpStatus.NOT_FOUND, errorResponse.getErrorCode());
        assertEquals("Resource not found", errorResponse.getErrorMessage());
        assertEquals(now, errorResponse.getErrorTime());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        ErrorResponseDto errorResponse1 = new ErrorResponseDto("/api/test", HttpStatus.BAD_REQUEST, "Invalid request", now);
        ErrorResponseDto errorResponse2 = new ErrorResponseDto("/api/test", HttpStatus.BAD_REQUEST, "Invalid request", now);

        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
    }

    @Test
    void testToStringContainsFields() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDto errorResponse = new ErrorResponseDto("/api/test", HttpStatus.BAD_REQUEST, "Invalid request", now);

        String toStringResult = errorResponse.toString();
        assertTrue(toStringResult.contains("/api/test"));
        assertTrue(toStringResult.contains("Invalid request"));
        assertTrue(toStringResult.contains("BAD_REQUEST"));
    }

    @Test
    void testDefaultValuesWithNoArgsConstructor() {
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        assertNull(errorResponse.getApiPath());
        assertNull(errorResponse.getErrorCode());
        assertNull(errorResponse.getErrorMessage());
        assertNull(errorResponse.getErrorTime());
    }
}
