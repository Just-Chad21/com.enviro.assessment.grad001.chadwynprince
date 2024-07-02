package modelTests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import myapp.model.RecyclingTip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecyclingTipTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        RecyclingTip recyclingTip = new RecyclingTip();

        // Test ID
        Long id = 1L;
        recyclingTip.setId(id);
        assertEquals(id, recyclingTip.getId());

        // Test tip
        String tip = "Recycle paper products.";
        recyclingTip.setTip(tip);
        assertEquals(tip, recyclingTip.getTip());
    }

    @Test
    void testValidRecyclingTip() {
        RecyclingTip recyclingTip = new RecyclingTip();
        recyclingTip.setTip("Recycle paper products.");

        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRecyclingTip() {
        RecyclingTip recyclingTip = new RecyclingTip();
        recyclingTip.setTip(null);

        Set<ConstraintViolation<RecyclingTip>> violations = validator.validate(recyclingTip);
        assertEquals(1, violations.size());

        ConstraintViolation<RecyclingTip> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("tip", violation.getPropertyPath().toString());
    }
}
