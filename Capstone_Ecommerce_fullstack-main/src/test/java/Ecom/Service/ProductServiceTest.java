package Ecom.Service;

import Ecom.Exception.ProductException;
import Ecom.Model.Product;
import Ecom.ModelDTO.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1);
        product.setName("Laptop");
        product.setPrice(50000.0);

        productDTO = new ProductDTO();
        productDTO.setName("Updated Laptop");
        productDTO.setPrice(55000.0);
    }

    @Test
    void addProduct_ShouldReturnProduct() throws ProductException {
        when(productService.addProduct(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertNotNull(result);
        assertEquals(1, result.getProductId());
        verify(productService).addProduct(product);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws ProductException {
        when(productService.updateProduct(1, productDTO)).thenReturn(product);

        Product result = productService.updateProduct(1, productDTO);

        assertEquals("Laptop", result.getName()); // Mock returns original product
        verify(productService).updateProduct(1, productDTO);
    }

    @Test
    void getProductByName_ShouldReturnList() throws ProductException {
        when(productService.getProductByName("Laptop")).thenReturn(List.of(product));

        List<Product> result = productService.getProductByName("Laptop");

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productService).getProductByName("Laptop");
    }

    @Test
    void getAllProduct_ShouldReturnList() throws ProductException {
        when(productService.getAllProduct("Laptop", "asc", "price")).thenReturn(List.of(product));

        List<Product> result = productService.getAllProduct("Laptop", "asc", "price");

        assertEquals(1, result.size());
        verify(productService).getAllProduct("Laptop", "asc", "price");
    }

    @Test
    void getProductByCategory_ShouldReturnList() throws ProductException {
        when(productService.getProductByCategory("Electronics")).thenReturn(List.of(product));

        List<Product> result = productService.getProductByCategory("Electronics");

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productService).getProductByCategory("Electronics");
    }

    @Test
    void removeProduct_ShouldNotThrowException() {
        assertDoesNotThrow(() -> productService.removeProduct(1));
        verify(productService).removeProduct(1);
    }

    @Test
    void getSingleProduct_ShouldReturnProduct() {
        when(productService.getSingleProduct(1)).thenReturn(product);

        Product result = productService.getSingleProduct(1);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productService).getSingleProduct(1);
    }
}