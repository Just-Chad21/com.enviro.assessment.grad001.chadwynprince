package servicesTests;

import myapp.model.RecyclingTip;
import myapp.repository.RecyclingTipRepository;
import myapp.services.RecyclingTipService;
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

public class RecyclingTipServiceTest {

    @Mock
    private RecyclingTipRepository repository;

    @InjectMocks
    private RecyclingTipService recyclingTipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateRecyclingTip() {
        // Given
        RecyclingTip recyclingTip = new RecyclingTip();
        recyclingTip.setId(1L);
        recyclingTip.setTip("New Tip");

        when(repository.save(any(RecyclingTip.class))).thenReturn(recyclingTip);

        // When
        RecyclingTip result = recyclingTipService.createRecyclingTip(recyclingTip);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("New Tip", result.getTip());
    }

    @Test
    void testGetAllRecyclingTips() {
        // Given
        RecyclingTip tip1 = new RecyclingTip();
        tip1.setId(1L);
        tip1.setTip("Tip 1");

        RecyclingTip tip2 = new RecyclingTip();
        tip2.setId(2L);
        tip2.setTip("Tip 2");

        when(repository.findAll()).thenReturn(Arrays.asList(tip1, tip2));

        // When
        List<RecyclingTip> result = recyclingTipService.getAllRecyclingTips();

        // Then
        assertEquals(2, result.size());
        assertEquals("Tip 1", result.get(0).getTip());
        assertEquals("Tip 2", result.get(1).getTip());
    }

    @Test
    void testGetRecyclingTipById() {
        // Given
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTip("Tip");

        when(repository.findById(1L)).thenReturn(Optional.of(tip));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<RecyclingTip> resultExisting = recyclingTipService.getRecyclingTipById(1L);
        Optional<RecyclingTip> resultNonExisting = recyclingTipService.getRecyclingTipById(2L);

        // Then
        assertTrue(resultExisting.isPresent());
        assertEquals("Tip", resultExisting.get().getTip());

        assertFalse(resultNonExisting.isPresent());
    }

    @Test
    void testUpdateRecyclingTip() {
        // Given
        RecyclingTip existingTip = new RecyclingTip();
        existingTip.setId(1L);
        existingTip.setTip("Existing Tip");

        RecyclingTip updatedTip = new RecyclingTip();
        updatedTip.setId(1L);
        updatedTip.setTip("Updated Tip");

        when(repository.findById(1L)).thenReturn(Optional.of(existingTip));
        when(repository.save(any(RecyclingTip.class))).thenReturn(updatedTip);

        // When
        RecyclingTip result = recyclingTipService.updateRecyclingTip(1L, updatedTip);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Updated Tip", result.getTip());
    }

    @Test
    void testUpdateRecyclingTip_NotFound() {
        // Given
        RecyclingTip updatedTip = new RecyclingTip();
        updatedTip.setId(1L);
        updatedTip.setTip("Updated Tip");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When
        RecyclingTip result = recyclingTipService.updateRecyclingTip(1L, updatedTip);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testDeleteRecyclingTip() {
        // Given
        doNothing().when(repository).deleteById(1L);

        // When
        recyclingTipService.deleteRecyclingTip(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }
}
