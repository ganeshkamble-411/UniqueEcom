package Ecom.ModelDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdminDTOTest {

    private Validator validator;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        adminDTO = new AdminDTO();
        adminDTO.setEmail("admin@example.com");
        adminDTO.setPassword("admin123");
        adminDTO.setFirstName("John");
        adminDTO.setLastName("Doe");
        adminDTO.setPhoneNumber("1234567890");
    }

    @Test
    void validAdminDTO_NoViolations() {
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid AdminDTO");
    }

    @Test
    void emailIsNull_ShouldFailValidation() {
        adminDTO.setEmail(null);
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email Id Is Mandatory")));
    }

    @Test
    void passwordTooShort_ShouldFailValidation() {
        adminDTO.setPassword("123"); // less than 5 chars
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Password length should be more than 5 Character")));
    }

    @Test
    void phoneNumberInvalidLength_ShouldFailValidation() {
        adminDTO.setPhoneNumber("12345"); // less than 10 digits
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Phone Number should be minumum 10 digit")));
    }

    @Test
    void firstNameBlank_ShouldFailValidation() {
        adminDTO.setFirstName(" ");
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("First Name can not be blank")));
    }

    @Test
    void lastNameBlank_ShouldFailValidation() {
        adminDTO.setLastName("");
        Set<ConstraintViolation<AdminDTO>> violations = validator.validate(adminDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Last Name can not be blank")));
    }
}
