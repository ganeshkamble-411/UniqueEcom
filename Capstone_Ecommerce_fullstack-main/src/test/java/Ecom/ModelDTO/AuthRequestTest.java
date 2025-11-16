package Ecom.ModelDTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testSettersAndGetters() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password123");

        assertEquals("testuser", authRequest.getUsername());
        assertEquals("password123", authRequest.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthRequest authRequest1 = new AuthRequest();
        authRequest1.setUsername("testuser");
        authRequest1.setPassword("password123");

        AuthRequest authRequest2 = new AuthRequest();
        authRequest2.setUsername("testuser");
        authRequest2.setPassword("password123");

        assertEquals(authRequest1, authRequest2);
        assertEquals(authRequest1.hashCode(), authRequest2.hashCode());
    }

    @Test
    void testToString() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("password123");

        String toStringResult = authRequest.toString();
        assertTrue(toStringResult.contains("testuser"));
        assertTrue(toStringResult.contains("password123"));
    }

    @Test
    void testDefaultValues() {
        AuthRequest authRequest = new AuthRequest();
        assertNull(authRequest.getUsername());
        assertNull(authRequest.getPassword());
    }
}