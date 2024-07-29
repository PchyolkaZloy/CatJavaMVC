package en.pchz.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.pchz.integration.IntegrationTestBase;
import en.pchz.common.request.CatMasterRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CatMasterControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Disabled
    @Test
    public void FindCatMasterById_Success() throws Exception {
        // Arrange
        Integer catMasterId = 1;

        // Act
        mockMvc.perform(get("/catMaster/{id}", catMasterId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }


    @Disabled
    @Test
    public void FindCatMasterById_NotFound() throws Exception {
        // Arrange
        Integer catMasterId = 10;

        // Act
        mockMvc.perform(get("/catMaster/{id}", catMasterId))
                .andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    public void CreateCatMaster_BadRequest() throws Exception {
        // Arrange
        CatMasterRequest request = new CatMasterRequest(null, null);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.post("/catMaster/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
