package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.WasteCategoryController;
import myapp.model.WasteCategory;
import myapp.modelAssemblers.WasteCategoryModelAssembler;
import myapp.services.WasteCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

public class WasteCategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WasteCategoryService wasteCategoryService;

    @InjectMocks
    private WasteCategoryController wasteCategoryController;

    @Mock
    private WasteCategoryModelAssembler wasteCategoryModelAssembler;

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

        EntityModel<WasteCategory> entityModel1 = EntityModel.of(
                category1,
                linkTo(WasteCategoryController.class).slash(category1.getId()).withSelfRel()
        );

        EntityModel<WasteCategory> entityModel2 = EntityModel.of(
                category2,
                linkTo(WasteCategoryController.class).slash(category2.getId()).withSelfRel()
        );

        CollectionModel<EntityModel<WasteCategory>> collectionModel = CollectionModel.of(
                Arrays.asList(entityModel1, entityModel2),
                linkTo(WasteCategoryController.class).withSelfRel()
        );

        when(wasteCategoryService.getAllWasteCategories()).thenReturn(Arrays.asList(category1, category2));
        when(wasteCategoryModelAssembler.toCollectionModel(any())).thenReturn(collectionModel);

        // When
        mockMvc.perform(get("/api/waste-categories"))
                .andExpect(status().isOk());

        // Then
        verify(wasteCategoryService, times(1)).getAllWasteCategories();
        verify(wasteCategoryModelAssembler, times(1)).toCollectionModel(any());
    }

    @Test
    void testGetWasteCategoryById() throws Exception {
        // Given
        WasteCategory category = new WasteCategory();
        category.setId(1L);
        category.setName("Category");

        EntityModel<WasteCategory> entityModel = EntityModel.of(
                category,
                linkTo(WasteCategoryController.class).slash(category.getId()).withSelfRel(),
                linkTo(WasteCategoryController.class).withRel("waste-categories")
        );

        when(wasteCategoryService.getWasteCategoryById(1L)).thenReturn(Optional.of(category));
        when(wasteCategoryModelAssembler.toModel(category)).thenReturn(entityModel);

        // When
        mockMvc.perform(get("/api/waste-categories/{id}", 1L))
                .andExpect(status().isOk());

        // Then
        verify(wasteCategoryService, times(1)).getWasteCategoryById(1L);
        verify(wasteCategoryModelAssembler, times(1)).toModel(category);
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
    void testDeleteWasteCategory() throws Exception {

        WasteCategory wasteCategory = new WasteCategory(1,"harzardous");
        // Given

        // When & Then
        when(wasteCategoryService.getWasteCategoryById(1L)).thenReturn(java.util.Optional.of(wasteCategory));

        mockMvc.perform(delete("/api/waste-categories/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verify that the service's delete method was called
        verify(wasteCategoryService, times(1)).deleteWasteCategory(1L);
    }
}
