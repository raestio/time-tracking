package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.utils.JsonUtils;
import cz.cvut.fit.timetracking.rest.utils.RequestCreationUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/sql_initialization_test_scripts/insert_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -2, authorities = {"USER", "ADMIN"})
    public void whenADMIN_shouldReturnUser() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("admin")))
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.surname", is("testovic")))
                .andExpect(jsonPath("$.email", is("admin@ahoj.cau")))
                .andExpect(jsonPath("$.userRoles[0].name", is("ADMIN")))
                .andExpect(jsonPath("$.userRoles[1].name", is("USER")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = "USER")
    public void whenUSERAndOwnedResource_shouldReturnUser() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("admin")))
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.surname", is("testovic")))
                .andExpect(jsonPath("$.email", is("admin@ahoj.cau")))
                .andExpect(jsonPath("$.userRoles[0].name", is("ADMIN")))
                .andExpect(jsonPath("$.userRoles[1].name", is("USER")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenAdminGetAll_shouldReturnUsers() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(4)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = "USER")
    public void whenUserGetAll_forbidden() throws Exception {
        mockMvc.perform(get(PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = "USER")
    public void whenUSER_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/-2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken
    public void whenNonAuthorities_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/-2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = "ADMIN")
    public void whenADMINOnly_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/-2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenAdminUpdate_returnUpdatedUser() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.userAndAdmin()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userRoles", hasSize(2)));

        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.userOnly()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userRoles", hasSize(1)))
                .andExpect(jsonPath("$.userRoles[0].name", is("USER")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenUpdateWithAdminOnly_returnBadRequestUserRoleCannotBeRemoved() throws Exception {
        mockMvc.perform(put(PATH + "/-2")
                .content(JsonUtils.toJsonString(RequestCreationUtils.adminOnly()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenUpdateNonExistingUser_returnNotFound() throws Exception {
        mockMvc.perform(put(PATH + "/-25684")
                .content(JsonUtils.toJsonString(RequestCreationUtils.adminOnly()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -2, authorities = "USER")
    public void whenMe_shouldReturnUser() throws Exception {
        mockMvc.perform(get(PATH + "/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("user")))
                .andExpect(jsonPath("$.id", is(-2)))
                .andExpect(jsonPath("$.surname", is("test")))
                .andExpect(jsonPath("$.email", is("user@ahoj2.cau")))
                .andExpect(jsonPath("$.userRoles[0].name", is("USER")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -2)
    public void whenMeWithoutRoles_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER", "ADMIN"})
    public void whenGetProjectsWithADMIN_shouldReturnUser() throws Exception {
        mockMvc.perform(get(PATH + "/-4/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projects", hasSize(2)))
                .andExpect(jsonPath("$.projects[0].name", is("test project")))
                .andExpect(jsonPath("$.projects[0].id", is(-1)))
                .andExpect(jsonPath("$.projects[0].start[0]", is(2019)))
                .andExpect(jsonPath("$.projects[0].start[1]", is(4)))
                .andExpect(jsonPath("$.projects[0].start[2]", is(4)))
                .andExpect(jsonPath("$.projects[0].workTypes", hasSize(2)))
                .andExpect(jsonPath("$.projects[0].workTypes[1].id", is(-1)))
                .andExpect(jsonPath("$.projects[0].workTypes[1].name", is("vyvoj")))
                .andExpect(jsonPath("$.projects[0].workTypes[0].id", is(-2)))
                .andExpect(jsonPath("$.projects[0].workTypes[0].name", is("analyza")))
                .andExpect(jsonPath("$.projects[1].name", is("test project 2")))
                .andExpect(jsonPath("$.projects[1].id", is(-2)))
                .andExpect(jsonPath("$.projects[1].start[0]", is(2019)))
                .andExpect(jsonPath("$.projects[1].start[1]", is(4)))
                .andExpect(jsonPath("$.projects[1].start[2]", is(10)))
                .andExpect(jsonPath("$.projects[1].workTypes", hasSize(1)))
                .andExpect(jsonPath("$.projects[1].workTypes[0].id", is(-1)))
                .andExpect(jsonPath("$.projects[1].workTypes[0].name", is("vyvoj")));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void whenGetMyProjects_shouldReturnUser() throws Exception {
        mockMvc.perform(get(PATH + "/me/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].name", is("test project")))
                .andExpect(jsonPath("$.projects[0].id", is(-1)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void whenGetAnotherUserProjects_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/-2/projects").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void whenGetAllUserRolesWithUser_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER", "ADMIN"})
    public void whenGetAllUserRolesWithAdmin_forbidden() throws Exception {
        mockMvc.perform(get(PATH + "/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userRoles", hasSize(2)))
                .andExpect(jsonPath("$.userRoles[0].name", is("USER")))
                .andExpect(jsonPath("$.userRoles[1].name", is("ADMIN")));
    }
}
