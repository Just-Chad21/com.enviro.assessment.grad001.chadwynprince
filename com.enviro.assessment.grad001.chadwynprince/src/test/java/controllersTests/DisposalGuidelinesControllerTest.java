package controllersTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import myapp.controllers.DisposalGuidelinesController;
import myapp.model.DisposalGuidelines;
import myapp.services.DisposalGuidelinesService;
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

public class DisposalGuidelinesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DisposalGuidelinesService disposalGuidelinesService;

    @InjectMocks
    private DisposalGuidelinesController disposalGuidelinesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(disposalGuidelinesController).build();
    }

    @Test
    void testGetAllDisposalGuidelines() throws Exception {
        // Given
        DisposalGuidelines guideline1 = new DisposalGuidelines();
        guideline1.setId(1L);
        guideline1.setDisposalGuideline("Guideline 1");

        DisposalGuidelines guideline2 = new DisposalGuidelines();
        guideline2.setId(2L);
        guideline2.setDisposalGuideline("Guideline 2");

        when(disposalGuidelinesService.getAllDisposalGuidelines()).thenReturn(Arrays.asList(guideline1, guideline2));

        // When
        mockMvc.perform(get("/api/disposal-guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].disposalGuideline").value("Guideline 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].disposalGuideline").value("Guideline 2"));

        // Then
        verify(disposalGuidelinesService, times(1)).getAllDisposalGuidelines();
    }

    @Test
    void testGetDisposalGuidelinesById() throws Exception {
        // Given
        DisposalGuidelines guideline = new DisposalGuidelines();
        guideline.setId(1L);
        guideline.setDisposalGuideline("Guideline");

        when(disposalGuidelinesService.getDisposalGuidelinesById(1L)).thenReturn(Optional.of(guideline));
        when(disposalGuidelinesService.getDisposalGuidelinesById(2L)).thenReturn(Optional.empty());

        // When
        mockMvc.perform(get("/api/disposal-guidelines/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.disposalGuideline").value("Guideline"));

        mockMvc.perform(get("/api/disposal-guidelines/{id}", 2L))
                .andExpect(status().isNotFound());

        // Then
        verify(disposalGuidelinesService, times(1)).getDisposalGuidelinesById(1L);
        verify(disposalGuidelinesService, times(1)).getDisposalGuidelinesById(2L);
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

        mockMvc.perform(put("/api/disposal-guidelines/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedGuideline)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testDeleteDisposalGuidelines() throws Exception {
        // When
        mockMvc.perform(delete("/api/disposal-guidelines/{id}", 1L))
                .andExpect(status().isNoContent());

        // Then
        verify(disposalGuidelinesService, times(1)).deleteDisposalGuidelines(1L);
    }
}
