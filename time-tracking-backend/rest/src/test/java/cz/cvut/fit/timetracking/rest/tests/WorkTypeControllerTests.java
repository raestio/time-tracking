package cz.cvut.fit.timetracking.rest.tests;


import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;
import cz.cvut.fit.timetracking.rest.utils.JsonUtils;
import cz.cvut.fit.timetracking.rest.utils.RequestCreationUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/sql_initialization_test_scripts/insert_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WorkTypeControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/work-types";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER"})
    public void testGetWorkTypesWithUser_expectForbidden() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void testGetWorkTypesWithUser_expectOk() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workTypes", hasSize(2)))
                .andExpect(jsonPath("$.workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[0].name", is("vyvoj")))
                .andExpect(jsonPath("$.workTypes[1].id", is(-2)))
                .andExpect(jsonPath("$.workTypes[1].name", is("analyza")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createAndFindAndDeleteWorkType() throws Exception {
        var result = mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workType()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("test work type")))
                .andExpect(jsonPath("$.description", is("ahoj")))
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var workType = JsonUtils.fromJsonString(content, WorkTypeDTO.class);
        mockMvc.perform(get(PATH + "/" + workType.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("test work type")))
                .andExpect(jsonPath("$.description", is("ahoj")))
                .andReturn();

        mockMvc.perform(delete(PATH + "/" + workType.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/" + workType.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateWorkType() throws Exception {
        mockMvc.perform(put(PATH + "/-1")
                .content(JsonUtils.toJsonString(RequestCreationUtils.workType()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.name", is("test work type")))
                .andExpect(jsonPath("$.description", is("ahoj")));

        mockMvc.perform(get(PATH + "/-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.name", is("test work type")))
                .andExpect(jsonPath("$.description", is("ahoj")));

        mockMvc.perform(put(PATH + "/-1")
                .content(JsonUtils.toJsonString(RequestCreationUtils.workType2()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.name", is("vyvoj")))
                .andExpect(jsonPath("$.description", nullValue()));

        mockMvc.perform(get(PATH + "/-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.name", is("vyvoj")))
                .andExpect(jsonPath("$.description", nullValue()));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateNonExistingWorkType_expectNotFound() throws Exception {
        mockMvc.perform(put(PATH + "/-199999")
                .content(JsonUtils.toJsonString(RequestCreationUtils.workType()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
