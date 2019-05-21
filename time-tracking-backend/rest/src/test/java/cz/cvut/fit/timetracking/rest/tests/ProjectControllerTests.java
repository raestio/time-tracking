package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/sql_initialization_test_scripts/insert_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/projects";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenGetAllWithAdmin_shouldReturnProjects() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projects", hasSize(3)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = "USER")
    public void whenGetAllWithUser_forbidden() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenGetById_shouldReturnProject() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test project")))
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.start[0]", is(2019)))
                .andExpect(jsonPath("$.start[1]", is(4)))
                .andExpect(jsonPath("$.start[2]", is(4)))
                .andExpect(jsonPath("$.workTypes", hasSize(2)))
                .andExpect(jsonPath("$.workTypes[1].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[1].name", is("vyvoj")))
                .andExpect(jsonPath("$.workTypes[0].id", is(-2)))
                .andExpect(jsonPath("$.workTypes[0].name", is("analyza")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createAndFindAndDeleteProject() throws Exception {
        var result = mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.project()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.start[0]", is(2019)))
                .andExpect(jsonPath("$.start[1]", is(5)))
                .andExpect(jsonPath("$.start[2]", is(1)))
                .andExpect(jsonPath("$.end[0]", is(2020)))
                .andExpect(jsonPath("$.end[1]", is(5)))
                .andExpect(jsonPath("$.end[2]", is(1)))
                .andExpect(jsonPath("$.workTypes", hasSize(1)))
                .andExpect(jsonPath("$.workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[0].name", is("vyvoj")))
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var project = JsonUtils.fromJsonString(content, ProjectDTO.class);
        mockMvc.perform(get(PATH + "/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.start[0]", is(2019)))
                .andExpect(jsonPath("$.start[1]", is(5)))
                .andExpect(jsonPath("$.start[2]", is(1)))
                .andExpect(jsonPath("$.end[0]", is(2020)))
                .andExpect(jsonPath("$.end[1]", is(5)))
                .andExpect(jsonPath("$.end[2]", is(1)))
                .andExpect(jsonPath("$.workTypes", hasSize(1)))
                .andExpect(jsonPath("$.workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[0].name", is("vyvoj")))
                .andReturn();

        mockMvc.perform(delete(PATH + "/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = "USER")
    public void createProjectWithUser_expectForbidden() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.project()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = "USER")
    public void updateProjectWithUser_expectForbidden() throws Exception {
        mockMvc.perform(put(PATH + "/-1")
                .content(JsonUtils.toJsonString(RequestCreationUtils.project()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateNonExistingProject_expectNotFound() throws Exception {
        mockMvc.perform(put(PATH + "/-984849")
                .content(JsonUtils.toJsonString(RequestCreationUtils.project()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateAndFindAndDeleteProject() throws Exception {
        mockMvc.perform(put(PATH + "/-3")
                .content(JsonUtils.toJsonString(RequestCreationUtils.project()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-3)))
                .andExpect(jsonPath("$.start[0]", is(2019)))
                .andExpect(jsonPath("$.start[1]", is(5)))
                .andExpect(jsonPath("$.start[2]", is(1)))
                .andExpect(jsonPath("$.end[0]", is(2020)))
                .andExpect(jsonPath("$.end[1]", is(5)))
                .andExpect(jsonPath("$.end[2]", is(1)))
                .andExpect(jsonPath("$.workTypes", hasSize(1)))
                .andExpect(jsonPath("$.workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[0].name", is("vyvoj")))
                .andReturn();

        mockMvc.perform(get(PATH + "/-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-3)))
                .andExpect(jsonPath("$.start[0]", is(2019)))
                .andExpect(jsonPath("$.start[1]", is(5)))
                .andExpect(jsonPath("$.start[2]", is(1)))
                .andExpect(jsonPath("$.end[0]", is(2020)))
                .andExpect(jsonPath("$.end[1]", is(5)))
                .andExpect(jsonPath("$.end[2]", is(1)))
                .andExpect(jsonPath("$.workTypes", hasSize(1)))
                .andExpect(jsonPath("$.workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.workTypes[0].name", is("vyvoj")))
                .andReturn();

        mockMvc.perform(delete(PATH + "/-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
