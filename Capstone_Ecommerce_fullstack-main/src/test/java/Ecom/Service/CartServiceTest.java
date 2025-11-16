package Ecom.Service;

import Ecom.Exception.CartException;
import Ecom.Model.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartService cartService;
    private Cart cart;


        @Test
        void removeProductFromCart_ShouldNotThrowException() {
            assertDoesNotThrow(() -> cartService.removeProductFromCart(1, 10));
            verify(cartService).removeProductFromCart(1, 10);
        }

        @Test
        void removeAllProductFromCart_ShouldNotThrowException() {
            assertDoesNotThrow(() -> cartService.removeAllProductFromCart(1));
            verify(cartService).removeAllProductFromCart(1);
        }


    }