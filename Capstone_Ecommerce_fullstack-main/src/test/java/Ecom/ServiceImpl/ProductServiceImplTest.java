//package Ecom.ServiceImpl;
//
//
//import Ecom.Exception.ProductException;
//import Ecom.Model.Product;
//import Ecom.ModelDTO.ProductDTO;
//import Ecom.Repository.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Sort;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceImplTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//    private Product product;
//    private ProductDTO productDTO;
//
//    @BeforeEach
//    void setUp() {
//
//        product.setName("Laptop"); // ✅ Added name
//        product.setPrice(50000.0);
//        product.setImageUrl("image.jpg");
//        product.setDescription("High-end laptop");
//
//        productDTO = new ProductDTO();
//        productDTO.setName("Updated Laptop");
//        productDTO.setCategory("Electronics");
//        productDTO.setPrice(55000.0);
//        productDTO.setImageUrl("updated.jpg");
//        productDTO.setDescription("Updated description");
//    }
//
//    @Test
//    void addProduct_ShouldSaveAndReturnProduct() throws ProductException {
//        when(productRepository.save(product)).thenReturn(product);
//
//        Product result = productService.addProduct(product);
//
//        assertNotNull(result);
//        assertEquals("Laptop", result.getName());
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    void addProduct_NullProduct_ShouldThrowException() {
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.addProduct(null));
//
//        assertEquals("Product Can not be Null", ex.getMessage());
//    }
//
//    @Test
//    void updateProduct_ValidId_ShouldUpdateAndReturnProduct() throws ProductException {
//        when(productRepository.findById(1)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        Product result = productService.updateProduct(1, productDTO);
//
//        assertEquals("Updated Laptop", result.getName());
//        assertEquals(55000.0, result.getPrice());
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    void updateProduct_InvalidId_ShouldThrowException() {
//        when(productRepository.findById(99)).thenReturn(Optional.empty());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.updateProduct(99, productDTO));
//
//        assertEquals("Product with ID 99 not found.", ex.getMessage());
//    }
//
//    @Test
//    void getProductByName_ShouldReturnList() throws ProductException {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//
//        when(productRepository.findByName("Laptop")).thenReturn(products);
//
//        List<Product> result = productService.getProductByName("Laptop");
//
//        assertEquals(1, result.size());
//        assertEquals("Laptop", result.get(0).getName());
//    }
//
//    @Test
//    void getProductByName_NotFound_ShouldThrowException() {
//        when(productRepository.findByName("Unknown")).thenReturn(new ArrayList<>());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.getProductByName("Unknown"));
//
//        assertEquals("Product Not found with name Unknown", ex.getMessage());
//    }
//
//    @Test
//    void getAllProduct_WithKeyword_ShouldReturnList() throws ProductException {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//
//        when(productRepository.findAllByNameContainingIgnoreCase("Lap", Sort.by(Sort.Direction.ASC, "price")))
//                .thenReturn(products);
//
//        List<Product> result = productService.getAllProduct("Lap", "asc", "price");
//
//        assertEquals(1, result.size());
//        assertEquals("Laptop", result.get(0).getName());
//    }
//
//    @Test
//    void getAllProduct_WithoutKeyword_ShouldReturnList() throws ProductException {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//
//        when(productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"))).thenReturn(products);
//
//        List<Product> result = productService.getAllProduct(null, "desc", "price");
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void getAllProduct_EmptyList_ShouldThrowException() {
//        when(productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"))).thenReturn(new ArrayList<>());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.getAllProduct(null, "asc", "price"));
//
//        assertEquals("Product List Empty", ex.getMessage());
//    }
//
//    @Test
//    void getProductByCategory_ShouldReturnList() throws ProductException {
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//
//        when(productRepository.getProductCategoryName("Electronics")).thenReturn(products);
//
//        List<Product> result = productService.getProductByCategory("Electronics");
//
//        assertEquals("Laptop", result.get(0).getName());
//    }
//
//    @Test
//    void getProductByCategory_NotFound_ShouldThrowException() {
//        when(productRepository.getProductCategoryName("Unknown")).thenReturn(new ArrayList<>());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.getProductByCategory("Unknown"));
//
//        assertEquals("Product with category Name Unknown not found.", ex.getMessage());
//    }
//
//    @Test
//    void removeProduct_ShouldDeleteProduct() throws ProductException {
//        when(productRepository.findById(1)).thenReturn(Optional.of(product));
//
//        productService.removeProduct(1);
//
//        verify(productRepository, times(1)).delete(product);
//    }
//
//    @Test
//    void removeProduct_NotFound_ShouldThrowException() {
//        when(productRepository.findById(99)).thenReturn(Optional.empty());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.removeProduct(99));
//
//        assertEquals("Product with ID 99 not found.", ex.getMessage());
//    }
//
//    @Test
//    void getSingleProduct_NotFound_ShouldThrowException() {
//        when(productRepository.findById(99)).thenReturn(Optional.empty());
//
//        ProductException ex = assertThrows(ProductException.class,
//                () -> productService.getSingleProduct(99));
//
//        assertEquals("Product not found", ex.getMessage());
//    }
//}