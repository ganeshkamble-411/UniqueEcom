package Ecom.controller;

import Ecom.Controller.CartController;
import Ecom.Model.Cart;
import Ecom.Service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductToCart() {

        ResponseEntity<Cart> response = cartController.addProductToCart(101, 202);
        assertEquals(201, response.getStatusCodeValue());
        verify(cartService, times(1)).addProductToCart(101, 202);
    }

    @Test
    void testIncreaseProductQuantity() {
        Cart cart = new Cart();
        when(cartService.increaseProductQuantity(101, 202)).thenReturn(cart);
        ResponseEntity<Cart> response = cartController.increaseProductQuantity(101, 202);
        assertEquals(200, response.getStatusCodeValue());
        verify(cartService, times(1)).increaseProductQuantity(101, 202);
    }

    @Test
    void testDecreaseProductQuantity() {
        Cart cart = new Cart();
        when(cartService.decreaseProductQuantity(1, 202)).thenReturn(cart);
        ResponseEntity<Cart> response = cartController.decreaseProductQuantity(1, 202);
        assertEquals(200, response.getStatusCodeValue());
        verify(cartService, times(1)).decreaseProductQuantity(1, 202);
    }

    @Test
    void testRemoveProductFromCart() {
        doNothing().when(cartService).removeProductFromCart(1, 202);
        ResponseEntity<String> response = cartController.removeProductFromCart(1, 202);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Prodcut is removed from cart", response.getBody());
        verify(cartService, times(1)).removeProductFromCart(1, 202);
    }

    @Test
    void testRemoveAllProductFromCart() {
        doNothing().when(cartService).removeAllProductFromCart(1);
        ResponseEntity<String> response = cartController.removeAllProductFromCart(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("All product Remove From cart", response.getBody());
        verify(cartService, times(1)).removeAllProductFromCart(1);
    }
}