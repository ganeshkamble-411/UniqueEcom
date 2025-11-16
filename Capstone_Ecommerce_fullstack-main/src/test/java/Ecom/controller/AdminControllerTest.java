package Ecom.controller;

import Ecom.Controller.AdminController;
import Ecom.Model.User;
import Ecom.ModelDTO.AdminDTO;
import Ecom.ModelDTO.UserDTO;
import Ecom.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        AdminDTO adminDTO = new AdminDTO();

        adminDTO.setPassword("plainPassword");

        User user = new User();


        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userService.addUserAdmin(adminDTO)).thenReturn(user);

        ResponseEntity<User> response = adminController.addUser(adminDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userService, times(1)).addUserAdmin(adminDTO);
    }

    @Test
    void testUpdateUserPassword() {
        UserDTO userDTO = new UserDTO();


        User updatedUser = new User();


        when(userService.changePassword(1, userDTO)).thenReturn(updatedUser);

        ResponseEntity<User> response = adminController.updateUserPassword(1, userDTO);
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).changePassword(1, userDTO);
    }
}

