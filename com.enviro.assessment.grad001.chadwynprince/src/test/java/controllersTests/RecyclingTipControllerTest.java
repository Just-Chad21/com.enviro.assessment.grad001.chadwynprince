package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.RecyclingTipController;
import myapp.model.RecyclingTip;
import myapp.modelAssemblers.RecyclingTipModelAssembler;
import myapp.services.RecyclingTipService;
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

public class RecyclingTipControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecyclingTipService recyclingTipService;

    @Mock
    private RecyclingTipModelAssembler recyclingTipModelAssembler;

    @InjectMocks
    private RecyclingTipController recyclingTipController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recyclingTipController).build();
    }

    @Test
    void testGetAllRecyclingTips() throws Exception {
        // Given
        RecyclingTip tip1 = new RecyclingTip(1L, "Tip 1");
        RecyclingTip tip2 = new RecyclingTip(2L, "Tip 2");

        EntityModel<RecyclingTip> entityModel1 = EntityModel.of(
                tip1,
                linkTo(RecyclingTipController.class).slash(tip1.getId()).withSelfRel()
        );

        EntityModel<RecyclingTip> entityModel2 = EntityModel.of(
                tip2,
                linkTo(RecyclingTipController.class).slash(tip2.getId()).withSelfRel()
        );

        CollectionModel<EntityModel<RecyclingTip>> collectionModel = CollectionModel.of(
                Arrays.asList(entityModel1, entityModel2),
                linkTo(RecyclingTipController.class).withSelfRel()
        );

        when(recyclingTipService.getAllRecyclingTips()).thenReturn(Arrays.asList(tip1, tip2));
        when(recyclingTipModelAssembler.toCollectionModel(any())).thenReturn(collectionModel);

        // When
        mockMvc.perform(get("/api/recycling-tips"))
                .andExpect(status().isOk());

        // Then
        verify(recyclingTipService, times(1)).getAllRecyclingTips();
        verify(recyclingTipModelAssembler, times(1)).toCollectionModel(any());
    }


    @Test
    void testGetRecyclingTipById() throws Exception {
        // Given
        RecyclingTip tip = new RecyclingTip(1L, "Tip");
        EntityModel<RecyclingTip> entityModel = EntityModel.of(
                tip,
                linkTo(RecyclingTipController.class).slash(tip.getId()).withSelfRel(),
                linkTo(RecyclingTipController.class).withRel("recycling-tips")
        );

        when(recyclingTipService.getRecyclingTipById(1L)).thenReturn(Optional.of(tip));
        when(recyclingTipModelAssembler.toModel(tip)).thenReturn(entityModel);

        // When
        mockMvc.perform(get("/api/recycling-tips/{id}", 1L))
                .andExpect(status().isOk());

        // Then
        verify(recyclingTipService, times(1)).getRecyclingTipById(1L);
        verify(recyclingTipModelAssembler, times(1)).toModel(tip);
    }



    @Test
    void testCreateRecyclingTip() throws Exception {
        // Given
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTip("New Tip");

        when(recyclingTipService.createRecyclingTip(any(RecyclingTip.class))).thenReturn(tip);

        // When
        mockMvc.perform(post("/api/recycling-tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tip)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tip").value("New Tip"));

        // Then
        verify(recyclingTipService, times(1)).createRecyclingTip(any(RecyclingTip.class));
    }

    @Test
    void testUpdateRecyclingTip() throws Exception {
        // Given
        RecyclingTip existingTip = new RecyclingTip();
        existingTip.setId(1L);
        existingTip.setTip("Existing Tip");

        RecyclingTip updatedTip = new RecyclingTip();
        updatedTip.setId(1L);
        updatedTip.setTip("Updated Tip");

        when(recyclingTipService.updateRecyclingTip(eq(1L), any(RecyclingTip.class))).thenReturn(updatedTip);
        when(recyclingTipService.updateRecyclingTip(eq(2L), any(RecyclingTip.class))).thenReturn(null);

        // When
        mockMvc.perform(put("/api/recycling-tips/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tip").value("Updated Tip"));
    }

    @Test
    void testDeleteRecyclingTip() throws Exception {
        // Given
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTip("Sample Recycling Tip");

        // Mock the service call to simulate the resource exists
        when(recyclingTipService.getRecyclingTipById(1L)).thenReturn(java.util.Optional.of(tip));

        // When & Then
        mockMvc.perform(delete("/api/recycling-tips/{id}", 1L))
                .andExpect(status().isNoContent()); // 204 No Content

        // Verify that the service's delete method was called
        verify(recyclingTipService, times(1)).deleteRecyclingTip(1L);
    }


}
