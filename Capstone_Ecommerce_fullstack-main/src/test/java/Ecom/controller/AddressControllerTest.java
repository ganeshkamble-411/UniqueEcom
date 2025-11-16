package Ecom.controller;

import Ecom.Controller.AddressController;
import Ecom.Model.Address;
import Ecom.Service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAddressToUser() {
        Address address = new Address();
        address.setCity("Pune");

        when(addressService.addAddressToUser(101, address)).thenReturn(address);
        ResponseEntity<Address> response = addressController.addAddressToUser(101, address);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Pune", response.getBody().getCity());
        verify(addressService, times(1)).addAddressToUser(101, address);
    }

    @Test
    void testUpdateAddress() {
        Address updatedAddress = new Address();
        updatedAddress.setCity("Mumbai");
        when(addressService.updateAddress(updatedAddress, 1)).thenReturn(updatedAddress);
        ResponseEntity<Address> response = addressController.updateAddress(updatedAddress, 1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mumbai", response.getBody().getCity());
        verify(addressService, times(1)).updateAddress(updatedAddress, 1);
    }


    @Test
    void testGetAllUserAddress() {
        Address address1 = new Address();
        address1.setCity("Pune");
        Address address2 = new Address();
        address2.setCity("Mumbai");

        when(addressService.getAllUserAddress(101)).thenReturn(Arrays.asList(address1, address2));
        ResponseEntity<List<Address>> response = addressController.getAllUserAddress(101);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Pune", response.getBody().get(0).getCity());
        verify(addressService, times(1)).getAllUserAddress(101);
    }
}