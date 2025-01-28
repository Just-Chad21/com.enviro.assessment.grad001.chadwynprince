package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.DisposalGuidelinesController;
import myapp.model.DisposalGuidelines;
import myapp.modelAssemblers.DisposalGuidelinesModelAssembler;
import myapp.services.DisposalGuidelinesService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DisposalGuidelinesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisposalGuidelinesService disposalGuidelinesService;

    @InjectMocks
    private DisposalGuidelinesController disposalGuidelinesController;

    @Mock
    private DisposalGuidelinesModelAssembler disposalGuidelinesModelAssembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disposalGuidelinesController).build();
    }

    @Test
    void testGetAllDisposalGuidelines() throws Exception {
        // Given
        DisposalGuidelines guideline1 = new DisposalGuidelines(1L, "Guideline 1");
        DisposalGuidelines guideline2 = new DisposalGuidelines(2L, "Guideline 2");

        EntityModel<DisposalGuidelines> entityModel1 = EntityModel.of(
                guideline1,
                linkTo(DisposalGuidelinesController.class).slash(guideline1.getId()).withSelfRel()
        );

        EntityModel<DisposalGuidelines> entityModel2 = EntityModel.of(
                guideline2,
                linkTo(DisposalGuidelinesController.class).slash(guideline2.getId()).withSelfRel()
        );

        CollectionModel<EntityModel<DisposalGuidelines>> collectionModel = CollectionModel.of(
                Arrays.asList(entityModel1, entityModel2),
                linkTo(DisposalGuidelinesController.class).withSelfRel()
        );

        when(disposalGuidelinesService.getAllDisposalGuidelines()).thenReturn(Arrays.asList(guideline1, guideline2));
        when(disposalGuidelinesModelAssembler.toCollectionModel(any())).thenReturn(collectionModel);

        // When
        mockMvc.perform(get("/api/disposal-guidelines"))
                .andExpect(status().isOk());

        // Then
        verify(disposalGuidelinesService, times(1)).getAllDisposalGuidelines();
        verify(disposalGuidelinesModelAssembler, times(1)).toCollectionModel(any());
    }


    @Test
    void testGetDisposalGuidelinesById() throws Exception {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines(1L, "Guideline");
        EntityModel<DisposalGuidelines> entityModel = EntityModel.of(
                guideline,
                linkTo(DisposalGuidelinesController.class).slash(guideline.getId()).withSelfRel(),
                linkTo(DisposalGuidelinesController.class).withRel("disposal-guidelines")
        );

        when(disposalGuidelinesService.getDisposalGuidelinesById(1L)).thenReturn(Optional.of(guideline));
        when(disposalGuidelinesModelAssembler.toModel(guideline)).thenReturn(entityModel);

        // When
        mockMvc.perform(get("/api/disposal-guidelines/{id}", 1L))
                .andExpect(status().isOk());

        // Then
        verify(disposalGuidelinesService, times(1)).getDisposalGuidelinesById(1L);
        verify(disposalGuidelinesModelAssembler, times(1)).toModel(guideline);
    }


    @Test
    void testCreateDisposalGuidelines() throws Exception {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setDisposalGuideline("New Guideline");

        when(disposalGuidelinesService.createDisposalGuidelines(any(DisposalGuidelines.class))).thenReturn(guideline);

        // When
        mockMvc.perform(post("/api/disposal-guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(guideline)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.disposalGuideline").value("New Guideline"));

        // Then
        verify(disposalGuidelinesService, times(1)).createDisposalGuidelines(any(DisposalGuidelines.class));
    }

    @Test
    void testUpdateDisposalGuidelines() throws Exception {
        // Given
        DisposalGuidelines existingGuideline = new DisposalGuidelines();
        existingGuideline.setId(1L);
        existingGuideline.setDisposalGuideline("Existing Guideline");

        DisposalGuidelines updatedGuideline = new DisposalGuidelines();
        updatedGuideline.setId(1L);
        updatedGuideline.setDisposalGuideline("Updated Guideline");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(existingGuideline);

        when(disposalGuidelinesService.updateDisposalGuidelines(eq(1L), any(DisposalGuidelines.class))).thenReturn(updatedGuideline);
        when(disposalGuidelinesService.updateDisposalGuidelines(eq(2L), any(DisposalGuidelines.class))).thenReturn(null);

        // When
        mockMvc.perform(put("/api/disposal-guidelines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.disposalGuideline").value("Updated Guideline"));

    }

    @Test
    void testDeleteDisposalGuidelines() throws Exception {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setDisposalGuideline("Sample Disposal Guideline");

        // Mock the service call to simulate the resource exists
        when(disposalGuidelinesService.getDisposalGuidelinesById(1L)).thenReturn(java.util.Optional.of(guideline));

        // When & Then
        mockMvc.perform(delete("/api/disposal-guidelines/{id}", 1L))
                .andExpect(status().isNoContent()); // 204 No Content

        // Verify that the service's delete method was called
        verify(disposalGuidelinesService, times(1)).deleteDisposalGuidelines(1L);
    }

}
