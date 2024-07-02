package modelTests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import myapp.model.DisposalGuidelines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisposalGuidelinesTests {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDisposalGuidelines() {
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines();
        disposalGuidelines.setDisposalGuideline("Dispose in a green bin");

        Set<ConstraintViolation<DisposalGuidelines>> violations = validator.validate(disposalGuidelines);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidDisposalGuidelines() {
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines();
        disposalGuidelines.setDisposalGuideline(null);

        Set<ConstraintViolation<DisposalGuidelines>> violations = validator.validate(disposalGuidelines);
        assertEquals(1, violations.size());

        ConstraintViolation<DisposalGuidelines> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("disposalGuideline", violation.getPropertyPath().toString());
    }

    @Test
    void testGettersAndSetters() {
        DisposalGuidelines disposalGuidelines = new DisposalGuidelines();

        // Test ID
        long id = 1L;
        disposalGuidelines.setId(id);
        assertEquals(id, disposalGuidelines.getId());

        // Test disposalGuideline
        String guideline = "Dispose in a green bin";
        disposalGuidelines.setDisposalGuideline(guideline);
        assertEquals(guideline, disposalGuidelines.getDisposalGuideline());
    }
}
