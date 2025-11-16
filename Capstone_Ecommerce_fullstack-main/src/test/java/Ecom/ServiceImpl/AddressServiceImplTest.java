package Ecom.ServiceImpl;

import Ecom.Exception.AddressException;
import Ecom.Model.Address;
import Ecom.Model.User;
import Ecom.Repository.AddressRepository;
import Ecom.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService; // ✅ FIXED

    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAddress(new ArrayList<>());

        address = new Address();
        address.setCity("Pune");
        address.setState("MH");
        address.setStreet("MG Road");
        address.setZipCode("411001");
        address.setFlatNo("A-101");
    }

    @Test
    void addAddressToUser_ShouldAddAndReturnAddress() throws AddressException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.addAddressToUser(1, address);

        assertNotNull(result);
        assertEquals("Pune", result.getCity());
        verify(userRepository, times(1)).save(user);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void addAddressToUser_UserNotFound_ShouldThrowException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> addressService.addAddressToUser(1, address));

        assertEquals("User Not Fouund", ex.getMessage());
    }

    @Test
    void updateAddress_ValidId_ShouldUpdateAndReturnAddress() throws AddressException {
        Address existingAddress = new Address();

        existingAddress.setCity("OldCity");

        when(addressRepository.findById(100)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(existingAddress)).thenReturn(existingAddress);

        address.setCity("NewCity");
        Address result = addressService.updateAddress(address, 100);

        assertEquals("NewCity", result.getCity());
        verify(addressRepository, times(1)).save(existingAddress);
    }

    @Test
    void updateAddress_NotFound_ShouldThrowException() {
        when(addressRepository.findById(100)).thenReturn(Optional.empty());

        AddressException ex = assertThrows(AddressException.class,
                () -> addressService.updateAddress(address, 100));

        assertEquals("Address not found", ex.getMessage());
    }

    @Test
    void removeAddress_ValidId_ShouldDeleteAddress() throws AddressException {
        when(addressRepository.findById(100)).thenReturn(Optional.of(address));

        addressService.removeAddress(100);

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    void removeAddress_NotFound_ShouldThrowException() {
        when(addressRepository.findById(100)).thenReturn(Optional.empty());

        AddressException ex = assertThrows(AddressException.class,
                () -> addressService.removeAddress(100));

        assertEquals("Address not found", ex.getMessage());
    }

    @Test
    void getAllUserAddress_ShouldReturnList() throws AddressException {
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        when(addressRepository.getUserAddressList(1)).thenReturn(addressList);

        List<Address> result = addressService.getAllUserAddress(1);

        assertEquals(1, result.size());
        assertEquals("Pune", result.get(0).getCity());
    }

    @Test
    void getAllUserAddress_Empty_ShouldThrowException() {
        when(addressRepository.getUserAddressList(1)).thenReturn(new ArrayList<>());

        AddressException ex = assertThrows(AddressException.class,
                () -> addressService.getAllUserAddress(1));

        assertEquals("User does not have any address", ex.getMessage());
    }
}