package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.utils.JsonUtils;
import cz.cvut.fit.timetracking.rest.utils.RequestCreationUtils;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
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
}
