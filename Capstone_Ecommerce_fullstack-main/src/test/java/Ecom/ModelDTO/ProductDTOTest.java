package Ecom.ModelDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    private Validator validator;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        productDTO = new ProductDTO();
        productDTO.setName("Laptop");
        productDTO.setImageUrl("http://example.com/image.jpg");
        productDTO.setDescription("High performance laptop with 16GB RAM and SSD storage.");
        productDTO.setPrice(999.99);
        productDTO.setCategory("Electronics");
    }

    @Test
    void validProductDTO_NoViolations() {
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid ProductDTO");
    }

    @Test
    void nameIsNull_ShouldFailValidation() {
        productDTO.setName(null);
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Product name is Mandatory")));
    }

    @Test
    void imageUrlBlank_ShouldFailValidation() {
        productDTO.setImageUrl(" ");
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Product Image is Mandatory")));
    }

    @Test
    void descriptionTooShort_ShouldFailValidation() {
        productDTO.setDescription("Short"); // less than 10 chars
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("size must be between 10 and 500")));
    }

    @Test
    void priceIsNull_ShouldFailValidation() {
        productDTO.setPrice(null);
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Product price is Mandatory")));
    }

    @Test
    void categoryBlank_ShouldFailValidation() {
        productDTO.setCategory("");
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Product category_name is Mandatory")));
    }

    @Test
    void multipleInvalidFields_ShouldReturnMultipleViolations() {
        productDTO.setName(null);
        productDTO.setDescription("short");
        productDTO.setPrice(null);

        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() >= 3, "Expected multiple validation errors");
    }
}