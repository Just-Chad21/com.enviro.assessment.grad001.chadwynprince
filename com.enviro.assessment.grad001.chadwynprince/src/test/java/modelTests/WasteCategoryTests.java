package modelTests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import myapp.model.WasteCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WasteCategoryTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        WasteCategory wasteCategory = new WasteCategory();
        // Test ID
        long id = 1L;
        wasteCategory.setId(id);
        assertEquals(id, wasteCategory.getId());

        // Test wasteCategory
        String name = "Paper";
        wasteCategory.setName(name);
        assertEquals(name, wasteCategory.getName());
    }

    @Test
    void testValidWasteCategory() {
        WasteCategory wasteCategory = new WasteCategory();
        wasteCategory.setName("Recyclable");

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(wasteCategory);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidWasteCategory() {
        WasteCategory wasteCategory = new WasteCategory();
        wasteCategory.setName(null);

        Set<ConstraintViolation<WasteCategory>> violations = validator.validate(wasteCategory);
        assertEquals(1, violations.size());

        ConstraintViolation<WasteCategory> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }
}
