package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.WasteCategoryController;
import myapp.model.WasteCategory;
import myapp.services.WasteCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

public class WasteCategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WasteCategoryService wasteCategoryService;

    @InjectMocks
    private WasteCategoryController wasteCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wasteCategoryController).build();
    }

    @Test
    void testGetAllWasteCategories() throws Exception {
        // Given
        WasteCategory category1 = new WasteCategory();
        category1.setId(1L);
        category1.setName("Category 1");

        WasteCategory category2 = new WasteCategory();
        category2.setId(2L);
        category2.setName("Category 2");

        when(wasteCategoryService.getAllWasteCategories()).thenReturn(Arrays.asList(category1, category2));

        // When/Then
        mockMvc.perform(get("/api/waste-categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    void testGetWasteCategoryById() throws Exception {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category");

        when(wasteCategoryService.getWasteCategoryById(1L)).thenReturn(Optional.of(category));

        // When/Then
        mockMvc.perform(get("/api/waste-categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category"));
    }

    @Test
    void testGetWasteCategoryById_NotFound() throws Exception {
        // Given
        when(wasteCategoryService.getWasteCategoryById(2L)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/waste-categories/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateWasteCategory() throws Exception {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("New Category");

        WasteCategory createdCategory = new WasteCategory();
        createdCategory.setId(1L);
        createdCategory.setName("New Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(category);

        when(wasteCategoryService.createWasteCategory(any(WasteCategory.class))).thenReturn(createdCategory);

        // When/Then
        mockMvc.perform(post("/api/waste-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testUpdateWasteCategory() throws Exception {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Updated Category");

        WasteCategory updatedCategory = new WasteCategory();
        updatedCategory.setId(1L);
        updatedCategory.setName("Updated Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(category);

        when(wasteCategoryService.updateWasteCategory(eq(1L), any(WasteCategory.class))).thenReturn(updatedCategory);

        // When/Then
        mockMvc.perform(put("/api/waste-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Category"));

    }

    @Test
    void testUpdateWasteCategory_NotFound() throws Exception {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Updated Category");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(category);

        when(wasteCategoryService.updateWasteCategory(eq(1L), any(WasteCategory.class))).thenReturn(null);

        // When/Then
        mockMvc.perform(put("/api/waste-categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteWasteCategory() throws Exception {
        // Given
        doNothing().when(wasteCategoryService).deleteWasteCategory(1L);

        // When/Then
        mockMvc.perform(delete("/api/waste-categories/1"))
                .andExpect(status().isNoContent());
    }
}
