package Ecom.controller;

import Ecom.Controller.ProductController;
import Ecom.Model.Product;
import Ecom.ModelDTO.ProductDTO;
import Ecom.Service.ProductService;
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

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        Product product = new Product();

        product.setName("Laptop");

        when(productService.addProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = productController.addProduct(product);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Laptop", response.getBody().getName());
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testUpdateProduct() {
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Laptop");

        Product updatedProduct = new Product();

        updatedProduct.setName("Updated Laptop");

        when(productService.updateProduct(1, updatedProductDTO)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(1, updatedProductDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Laptop", response.getBody().getName());
        verify(productService, times(1)).updateProduct(1, updatedProductDTO);
    }

    @Test
    void testGetProductByName() {
        Product product = new Product();
        product.setName("Laptop");

        when(productService.getProductByName("Laptop")).thenReturn(Arrays.asList(product));

        ResponseEntity<List<Product>> response = productController.getProductByName("Laptop");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getName());
        verify(productService, times(1)).getProductByName("Laptop");
    }

    @Test
    void testSearchProducts() {
        Product product1 = new Product();
        product1.setName("Laptop");
        Product product2 = new Product();
        product2.setName("Phone");

        when(productService.getAllProduct("tech", "asc", "price")).thenReturn(Arrays.asList(product1, product2));

        ResponseEntity<List<Product>> response = productController.search("tech", "asc", "price");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(productService, times(1)).getAllProduct("tech", "asc", "price");
    }

    @Test
    void testGetProductByCategory() {
        Product product = new Product();
        product.setName("Laptop");

        when(productService.getProductByCategory("Electronics")).thenReturn(Arrays.asList(product));

        ResponseEntity<List<Product>> response = productController.getProductByCategory("Electronics");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Laptop", response.getBody().get(0).getName());
        verify(productService, times(1)).getProductByCategory("Electronics");
    }

    @Test
    void testGetSingleProduct() {
        Product product = new Product();

        product.setName("Laptop");

        when(productService.getSingleProduct(1)).thenReturn(product);

        ResponseEntity<Product> response = productController.getSingleProduct(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Laptop", response.getBody().getName());
        verify(productService, times(1)).getSingleProduct(1);
    }

    @Test
    void testRemoveProduct() {
        doNothing().when(productService).removeProduct(1);

        ResponseEntity<String> response = productController.removeProduct(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product removed successfully.", response.getBody());
        verify(productService, times(1)).removeProduct(1);
    }
}