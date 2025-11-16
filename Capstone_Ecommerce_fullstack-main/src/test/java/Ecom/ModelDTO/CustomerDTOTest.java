package Ecom.ModelDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCustomerDTO_ShouldPassValidation() {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        customer.setPassword("secret123");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("9876543210");

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);
        assertTrue(violations.isEmpty(), "Expected no validation errors for valid CustomerDTO");
    }

    @Test
    void emailNull_ShouldFailValidation() {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail(null);
        customer.setPassword("secret123");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("9876543210");

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email Id Is Mandatory")));
    }

    @Test
    void passwordTooShort_ShouldFailValidation() {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        customer.setPassword("123"); // too short
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("9876543210");

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Password length should be more than 5 Character")));
    }

    @Test
    void phoneNumberInvalidLength_ShouldFailValidation() {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        customer.setPassword("secret123");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("12345"); // invalid length

        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customer);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Phone Number should be minumum 10 digit")));
    }
}