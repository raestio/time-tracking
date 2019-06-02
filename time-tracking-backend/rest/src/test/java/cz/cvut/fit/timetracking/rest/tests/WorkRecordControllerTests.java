package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Rastislav Zlacky (rastislav.zlacky@inventi.cz) on 01.06.2019.
 */
@Sql(scripts = "/sql_initialization_test_scripts/insert_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_for_integration_tests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WorkRecordControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/work-records";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void whenGetMyWorkRecordById_shouldReturnWorkRecord() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-1)))
                .andExpect(jsonPath("$.description", is("test popis")))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.workType.id", is(-1)))
                .andExpect(jsonPath("$.workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(4)))
                .andExpect(jsonPath("$.dateFrom[2]", is(15)))
                .andExpect(jsonPath("$.dateFrom[3]", is(8)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(4)))
                .andExpect(jsonPath("$.dateTo[2]", is(15)))
                .andExpect(jsonPath("$.dateTo[3]", is(16)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void whenOtherUserWorkRecordByIdAndIamProjectManager_shouldReturnWorkRecord() throws Exception {
        mockMvc.perform(get(PATH + "/-7").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-7)))
                .andExpect(jsonPath("$.description", is("test popis")))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.workType.id", is(-1)))
                .andExpect(jsonPath("$.workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(4)))
                .andExpect(jsonPath("$.dateFrom[2]", is(15)))
                .andExpect(jsonPath("$.dateFrom[3]", is(8)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(4)))
                .andExpect(jsonPath("$.dateTo[2]", is(15)))
                .andExpect(jsonPath("$.dateTo[3]", is(16)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenOtherUserWorkRecordByIdAndIamAdmin_shouldReturnWorkRecord() throws Exception {
        mockMvc.perform(get(PATH + "/-8").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(-8)))
                .andExpect(jsonPath("$.description", is("test popis")))
                .andExpect(jsonPath("$.project.id", is(-2)))
                .andExpect(jsonPath("$.workType.id", is(-2)))
                .andExpect(jsonPath("$.workType.name", is("analyza")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(4)))
                .andExpect(jsonPath("$.dateFrom[2]", is(16)))
                .andExpect(jsonPath("$.dateFrom[3]", is(8)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(4)))
                .andExpect(jsonPath("$.dateTo[2]", is(16)))
                .andExpect(jsonPath("$.dateTo[3]", is(16)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -2, authorities = {"USER"})
    public void whenOtherUserWorkRecordByIdAndIamNotProjectManager_isForbidden() throws Exception {
        mockMvc.perform(get(PATH + "/-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
