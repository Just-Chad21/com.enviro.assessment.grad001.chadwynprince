package servicesTests;

import myapp.model.WasteCategory;
import myapp.repository.WasteCategoryRepository;
import myapp.services.WasteCategoryService;
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

public class WasteCategoryServiceTest {

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    @InjectMocks
    private WasteCategoryService wasteCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllWasteCategories() {
        // Given
        WasteCategory category1 = new WasteCategory();
        category1.setId(1L);
        category1.setName("Category 1");

        WasteCategory category2 = new WasteCategory();
        category2.setId(2L);
        category2.setName("Category 2");

        when(wasteCategoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // When
        List<WasteCategory> result = wasteCategoryService.getAllWasteCategories();

        // Then
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());
    }

    @Test
    void testGetWasteCategoryById() {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category");

        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(wasteCategoryRepository.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<WasteCategory> resultExisting = wasteCategoryService.getWasteCategoryById(1L);
        Optional<WasteCategory> resultNonExisting = wasteCategoryService.getWasteCategoryById(2L);

        // Then
        assertTrue(resultExisting.isPresent());
        assertEquals("Category", resultExisting.get().getName());

        assertFalse(resultNonExisting.isPresent());
    }

    @Test
    void testCreateWasteCategory() {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category");

        when(wasteCategoryRepository.save(any(WasteCategory.class))).thenReturn(category);

        // When
        WasteCategory result = wasteCategoryService.createWasteCategory(category);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Category", result.getName());
    }

    @Test
    void testUpdateWasteCategory() {
        // Given
        WasteCategory existingCategory = new WasteCategory();
        existingCategory.setId(1L);
        existingCategory.setName("Existing Category");

        WasteCategory updatedCategory = new WasteCategory();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(wasteCategoryRepository.save(any(WasteCategory.class))).thenReturn(updatedCategory);

        // When
        WasteCategory result = wasteCategoryService.updateWasteCategory(1L, updatedCategory);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Updated Category", result.getName());
    }

    @Test
    void testUpdateWasteCategory_NotFound() {
        // Given
        WasteCategory updatedCategory = new WasteCategory();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        when(wasteCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        WasteCategory result = wasteCategoryService.updateWasteCategory(1L, updatedCategory);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testDeleteWasteCategory() {
        // Given
        doNothing().when(wasteCategoryRepository).deleteById(1L);

        // When
        wasteCategoryService.deleteWasteCategory(1L);

        // Then
        verify(wasteCategoryRepository, times(1)).deleteById(1L);
    }
}
