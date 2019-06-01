package cz.cvut.fit.timetracking.rest.tests;


import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectAssignmentDTO;
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
public class ProjectAssignmentControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/project-assignments";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void testGetById() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.project.name", is("test project")))
                .andExpect(jsonPath("$.user.name", is("user but project manager")))
                .andExpect(jsonPath("$.user.id", is(-3)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(4)))
                .andExpect(jsonPath("$.validFrom[2]", is(4)))
                .andExpect(jsonPath("$.projectRoles", hasSize(2)))
                .andExpect(jsonPath("$.projectRoles[1].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles[0].name", is("PROJECT_MANAGER")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createAndFindAndDeleteProjectAssignmentDefaultRole() throws Exception {
        var result = mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentWithoutRole(-1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.project.name", is("test project")))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(1)))
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var assignment = JsonUtils.fromJsonString(content, ProjectAssignmentDTO.class);
        mockMvc.perform(get(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.project.name", is("test project")))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(1)))
                .andReturn();

        mockMvc.perform(delete(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test(timeout = 1000)
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createAndFindAndDeleteProjectAssignmentProjectManager() throws Exception {
        var result = mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentProjectManager(-1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectRoles[0].name", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.projectRoles[1].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(2)))
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var assignment = JsonUtils.fromJsonString(content, ProjectAssignmentDTO.class);
        mockMvc.perform(get(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectRoles[0].name", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.projectRoles[1].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(2)))
                .andReturn();

        mockMvc.perform(delete(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/" + assignment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test(timeout = 1000)
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateProjectAssignment() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentProjectManagerAndMember(-1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-2)))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.projectRoles[1].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(2)));

        mockMvc.perform(get(PATH + "/-2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(-2)))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("PROJECT_MANAGER")))
                .andExpect(jsonPath("$.projectRoles[1].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(2)));

        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentMember(-1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-2)))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.project.name", is("test project")))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(1)));

        mockMvc.perform(get(PATH + "/-2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-2)))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.project.name", is("test project")))
                .andExpect(jsonPath("$.user.name", is("admin")))
                .andExpect(jsonPath("$.user.id", is(-1)))
                .andExpect(jsonPath("$.validFrom[0]", is(2019)))
                .andExpect(jsonPath("$.validFrom[1]", is(5)))
                .andExpect(jsonPath("$.validFrom[2]", is(1)))
                .andExpect(jsonPath("$.projectRoles[0].name", is("MEMBER")))
                .andExpect(jsonPath("$.projectRoles", hasSize(1)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void updateProjectAssignmentWithProjectManagerRoleOnly_expectBadRequest() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentProjectManager(-1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void duplicateProjectAssignmentOnSameProject_expectBadRequest() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentMember(-3)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentProjectManager(-3)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void validToBeforeValidFrom_expectBadRequest() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.projectAssignmentMember2(-3)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
