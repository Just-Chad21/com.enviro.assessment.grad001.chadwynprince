package servicesTests;

import myapp.model.DisposalGuidelines;
import myapp.repository.DisposalGuidelinesRepository;
import myapp.services.DisposalGuidelinesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DisposalGuidelinesServiceTest {

    @Mock
    private DisposalGuidelinesRepository disposalGuidelinesRepository;

    @InjectMocks
    private DisposalGuidelinesService disposalGuidelinesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllDisposalGuidelines() {
        // Given
        DisposalGuidelines guideline1 = new DisposalGuidelines();
        guideline1.setId(1L);
        guideline1.setDisposalGuideline("Guideline 1");

        DisposalGuidelines guideline2 = new DisposalGuidelines();
        guideline2.setId(2L);
        guideline2.setDisposalGuideline("Guideline 2");

        when(disposalGuidelinesRepository.findAll()).thenReturn(Arrays.asList(guideline1, guideline2));

        // When
        List<DisposalGuidelines> result = disposalGuidelinesService.getAllDisposalGuidelines();

        // Then
        assertEquals(2, result.size());
        assertEquals("Guideline 1", result.get(0).getDisposalGuideline());
        assertEquals("Guideline 2", result.get(1).getDisposalGuideline());
    }

    @Test
    void testGetDisposalGuidelinesById() {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setDisposalGuideline("Guideline");

        when(disposalGuidelinesRepository.findById(1L)).thenReturn(Optional.of(guideline));
        when(disposalGuidelinesRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<DisposalGuidelines> resultExisting = disposalGuidelinesService.getDisposalGuidelinesById(1L);
        Optional<DisposalGuidelines> resultNonExisting = disposalGuidelinesService.getDisposalGuidelinesById(2L);

        // Then
        assertTrue(resultExisting.isPresent());
        assertEquals("Guideline", resultExisting.get().getDisposalGuideline());

        assertFalse(resultNonExisting.isPresent());
    }

    @Test
    void testCreateDisposalGuidelines() {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setDisposalGuideline("New Guideline");

        when(disposalGuidelinesRepository.save(any(DisposalGuidelines.class))).thenReturn(guideline);

        // When
        DisposalGuidelines result = disposalGuidelinesService.createDisposalGuidelines(guideline);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("New Guideline", result.getDisposalGuideline());
    }

    @Test
    void testUpdateDisposalGuidelines() {
        // Given
        DisposalGuidelines existingGuideline = new DisposalGuidelines();
        existingGuideline.setId(1L);
        existingGuideline.setDisposalGuideline("Existing Guideline");

        DisposalGuidelines updatedGuideline = new DisposalGuidelines();
        updatedGuideline.setId(1L);
        updatedGuideline.setDisposalGuideline("Updated Guideline");

        when(disposalGuidelinesRepository.findById(1L)).thenReturn(Optional.of(existingGuideline));
        when(disposalGuidelinesRepository.save(any(DisposalGuidelines.class))).thenReturn(updatedGuideline);

        // When
        DisposalGuidelines result = disposalGuidelinesService.updateDisposalGuidelines(1L, updatedGuideline);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Updated Guideline", result.getDisposalGuideline());
    }

    @Test
    void testUpdateDisposalGuidelines_NotFound() {
        // Given
        DisposalGuidelines updatedGuideline = new DisposalGuidelines();
        updatedGuideline.setId(1L);
        updatedGuideline.setDisposalGuideline("Updated Guideline");

        when(disposalGuidelinesRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        DisposalGuidelines result = disposalGuidelinesService.updateDisposalGuidelines(1L, updatedGuideline);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testDeleteDisposalGuidelines() {
        // Given
        doNothing().when(disposalGuidelinesRepository).deleteById(1L);

        // When
        disposalGuidelinesService.deleteDisposalGuidelines(1L);

        // Then
        verify(disposalGuidelinesRepository, times(1)).deleteById(1L);
    }
}
