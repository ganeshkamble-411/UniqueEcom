//package Ecom.controller;
//
//import Ecom.ModelDTO.AuthRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Map;
//
//class LoginController {
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testLoginSuccess() {
//        // Arrange
//        AuthRequest authRequest = new AuthRequest();
//        authRequest.setUsername("john");
//        authRequest.setPassword("password");
//    }
//
//    @Test
//    void testLoginFailure() {
//        // Arrange
//        AuthRequest authRequest = new AuthRequest();
//        authRequest.setUsername("john");
//        authRequest.setPassword("wrongpassword");
//    }
//}