package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.RecyclingTipController;
import myapp.model.RecyclingTip;
import myapp.services.RecyclingTipService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecyclingTipControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecyclingTipService recyclingTipService;

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
        RecyclingTip tip1 = new RecyclingTip();
        tip1.setId(1L);
        tip1.setTip("Tip 1");

        RecyclingTip tip2 = new RecyclingTip();
        tip2.setId(2L);
        tip2.setTip("Tip 2");

        when(recyclingTipService.getAllRecyclingTips()).thenReturn(Arrays.asList(tip1, tip2));

        // When
        mockMvc.perform(get("/api/recycling-tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].tip").value("Tip 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].tip").value("Tip 2"));

        // Then
        verify(recyclingTipService, times(1)).getAllRecyclingTips();
    }

    @Test
    void testGetRecyclingTipById() throws Exception {
        // Given
        RecyclingTip tip = new RecyclingTip();
        tip.setId(1L);
        tip.setTip("Tip");

        when(recyclingTipService.getRecyclingTipById(1L)).thenReturn(Optional.of(tip));
        when(recyclingTipService.getRecyclingTipById(2L)).thenReturn(Optional.empty());

        // When
        mockMvc.perform(get("/api/recycling-tips/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tip").value("Tip"));

        mockMvc.perform(get("/api/recycling-tips/{id}", 2L))
                .andExpect(status().isNotFound());

        // Then
        verify(recyclingTipService, times(1)).getRecyclingTipById(1L);
        verify(recyclingTipService, times(1)).getRecyclingTipById(2L);
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
                .andExpect(status().isOk())
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
        when(recyclingTipService.updateRecyclingTip(2L, updatedTip)).thenReturn(null);

        // When
        mockMvc.perform(put("/api/recycling-tips/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tip").value("Updated Tip"));

        mockMvc.perform(put("/api/recycling-tips/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTip)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testDeleteRecyclingTip() throws Exception {
        // When
        mockMvc.perform(delete("/api/recycling-tips/{id}", 1L))
                .andExpect(status().isNoContent());

        // Then
        verify(recyclingTipService, times(1)).deleteRecyclingTip(1L);
    }
}
