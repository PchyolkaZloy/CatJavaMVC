package en.pchz.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import en.pchz.CatResponseMap;
import en.pchz.common.request.CatRequest;
import en.pchz.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithUserDetails("John123")
public class UserCatControllerTests extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

    @Test
    public void FindCatById_UserIsAuth_Success() throws Exception {
        // Arrange
        Integer catId = 1;

        // Act
        mockMvc.perform(get("/user/cat/{id}", catId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CatResponseMap.catResponseMap.get("Fluffy").id()))
                .andExpect(jsonPath("$.name").value(CatResponseMap.catResponseMap.get("Fluffy").name()))
                .andExpect(jsonPath("$.birthdate").value(CatResponseMap.catResponseMap.get("Fluffy").birthdate().toString()))
                .andExpect(jsonPath("$.breed").value(CatResponseMap.catResponseMap.get("Fluffy").breed()))
                .andExpect(jsonPath("$.color").value(CatResponseMap.catResponseMap.get("Fluffy").color()))
                .andExpect(jsonPath("$.catMasterId").value(CatResponseMap.catResponseMap.get("Fluffy").catMasterId()));
    }

    @WithUserDetails("sa")
    @Test
    public void FindCatById_AdminIsAuth_Failed() throws Exception {
        // Arrange
        Integer catId = 1;

        // Act
        mockMvc.perform(get("/user/cat/{id}", catId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    public void FindCatById_IncorrectId_NotFound() throws Exception {
        // Arrange
        Integer catId = 10;

        // Act
        mockMvc.perform(get("/user/cat/{id}", catId))
                .andExpect(status().isNotFound());
    }


    @Test
    public void CreateCat_GoodStuff_Ok() throws Exception {
        // Arrange
        CatRequest request = new CatRequest("test", LocalDate.now(), "test", "test");

        // Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/cat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateCat_InvalidData_BadRequest() throws Exception {
        // Arrange
        CatRequest request = new CatRequest(null, null, null, null);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/cat/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void FindAllCatFriends_UserIsAuth_Success() throws Exception {
        // Arrange
        Integer catId = 3;

        // Act
        mockMvc.perform(get("/user/cat/getAllCatFriends?id={id}", catId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(CatResponseMap.catResponseMap.get("Fluffy").id()))
                .andExpect(jsonPath("$[0].name").value(CatResponseMap.catResponseMap.get("Fluffy").name()))
                .andExpect(jsonPath("$[0].birthdate").value(CatResponseMap.catResponseMap.get("Fluffy").birthdate().toString()))
                .andExpect(jsonPath("$[0].breed").value(CatResponseMap.catResponseMap.get("Fluffy").breed()))
                .andExpect(jsonPath("$[0].color").value(CatResponseMap.catResponseMap.get("Fluffy").color()))
                .andExpect(jsonPath("$[0].catMasterId").value(CatResponseMap.catResponseMap.get("Fluffy").catMasterId()));
    }

    @Test
    public void FindAllCatFriends_UserHasNotCat_BadRequest() throws Exception {
        // Arrange
        Integer catId = 2;

        // Act
        mockMvc.perform(get("/user/cat/getAllCatFriends?id={id}", catId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
