package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;
import cz.cvut.fit.timetracking.rest.utils.JsonUtils;
import cz.cvut.fit.timetracking.rest.utils.RequestCreationUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -4, authorities = {"USER"})
    public void createAndFindAndUpdateAndDeleteWorkRecord() throws Exception {
        var result = mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(null, -1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.workType.id", is(-1)))
                .andExpect(jsonPath("$.workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(5)))
                .andExpect(jsonPath("$.dateFrom[2]", is(1)))
                .andExpect(jsonPath("$.dateFrom[3]", is(0)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(5)))
                .andExpect(jsonPath("$.dateTo[2]", is(1)))
                .andExpect(jsonPath("$.dateTo[3]", is(8)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)))
                .andReturn();

        var content = result.getResponse().getContentAsString();
        var workRecord = JsonUtils.fromJsonString(content, WorkRecordDTO.class);
        mockMvc.perform(get(PATH + "/" + workRecord.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(workRecord.getId())))
                .andExpect(jsonPath("$.project.id", is(-1)))
                .andExpect(jsonPath("$.workType.id", is(-1)))
                .andExpect(jsonPath("$.workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(5)))
                .andExpect(jsonPath("$.dateFrom[2]", is(1)))
                .andExpect(jsonPath("$.dateFrom[3]", is(0)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(5)))
                .andExpect(jsonPath("$.dateTo[2]", is(1)))
                .andExpect(jsonPath("$.dateTo[3]", is(8)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)))
                .andReturn();

         mockMvc.perform(put(PATH + "/" + workRecord.getId())
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord2(null, -2)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.project.id", is(-2)))
                .andExpect(jsonPath("$.workType.id", is(-1)))
                .andExpect(jsonPath("$.workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.dateFrom[1]", is(5)))
                .andExpect(jsonPath("$.dateFrom[2]", is(29)))
                .andExpect(jsonPath("$.dateFrom[3]", is(0)))
                .andExpect(jsonPath("$.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.dateTo[1]", is(5)))
                .andExpect(jsonPath("$.dateTo[2]", is(29)))
                .andExpect(jsonPath("$.dateTo[3]", is(8)))
                .andExpect(jsonPath("$.dateTo[4]", is(0)))
                .andReturn();

        mockMvc.perform(delete(PATH + "/" + workRecord.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH + "/" + workRecord.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -4, authorities = {"USER"})
    public void createWorkRecordForDifferentUser_expectForbidden() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-1, -1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createWorkRecordForDifferentUserWithAdminWithNoProjectAssignment_expectBadRequest() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-1, -1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void createWorkRecordForDifferentUserWithAdmin_expectOk() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-4, -1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void createWorkRecordForDifferentUserWithProjectManagerOnDifferentProject_expectForbidden() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-4, -2)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER", "ADMIN"})
    public void createWorkRecordForDifferentUserWithProjectManagerAndAdminOnDifferentProject_expectOk() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-4, -2)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void createWorkRecordForDifferentUserWithProjectManager_expectOk() throws Exception {
        mockMvc.perform(post(PATH)
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecord(-4, -1)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void getMyWorkRecords() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(4)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void getMyWorkRecordsDefault() throws Exception {
        mockMvc.perform(get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(0)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void getMyWorkRecordsWithParam_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30&userId=-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(4)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void getMyWorkRecordsAndFilterByProject_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30&projectId=-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(1)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER"})
    public void getOtherUserWorkRecords_expectForbidden() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30&userId=-2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = {"USER", "ADMIN"})
    public void getOtherUserWorkRecordsWithAdmin_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30&userId=-2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(3)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = {"USER"})
    public void getOtherUserWorkRecordsWithProjectManager_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "?from=2019-04-01&to=2019-04-30&userId=-1&projectId=-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(3)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = { "USER" })
    public void testJiraUserAvailability_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "/jira/availability")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUserAvailable", is(true)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -1, authorities = { "USER" })
    public void testJiraUserAvailability_expectOkAndUserIsNotAvailable() throws Exception {
        mockMvc.perform(get(PATH + "/jira/availability")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUserAvailable", is(false)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = { "USER" })
    public void testJiraUserAvailability_expectForbidden() throws Exception {
        mockMvc.perform(get(PATH + "/jira/availability?userId=-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = { "USER", "ADMIN" })
    public void testJiraUserAvailabilityWithAdmin_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "/jira/availability?userId=-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isUserAvailable", is(true)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = { "USER", "ADMIN" })
    public void testGetWorkRecordsToImportFromJira_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "/jira?from=2019-05-29&to=2019-06-03&userId=-3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -3, authorities = { "USER" })
    public void testGetWorkRecordsToImportFromJiraMe_expectOk() throws Exception {
        mockMvc.perform(get(PATH + "/jira/me?from=2019-05-29&to=2020-06-03")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableProjects", hasSize(1)))
                .andExpect(jsonPath("$.availableProjects[0].id", is(-1)))
                .andExpect(jsonPath("$.availableProjects[0].name", is("test google project")))
                .andExpect(jsonPath("$.workRecordImports", hasSize(4)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.project.name", is("test project")))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateFrom[0]", is(2019)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateFrom[1]", is(5)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateFrom[2]", is(29)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateFrom[3]", is(9)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateFrom[4]", is(0)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateTo[0]", is(2019)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateTo[1]", is(5)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateTo[2]", is(29)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateTo[3]", is(15)))
                .andExpect(jsonPath("$.workRecordImports[0].workRecord.dateTo[4]", is(0)))
                .andExpect(jsonPath("$.workRecordImports[0].isInConflict", is(false)))
                .andExpect(jsonPath("$.workRecordImports[1].workRecord.project.name", is("test project")))
                .andExpect(jsonPath("$.workRecordImports[1].isInConflict", is(false)))
                .andExpect(jsonPath("$.workRecordImports[3].isInConflict", is(true)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -4, authorities = {"USER"})
    public void testBulkCreate() throws Exception {
        mockMvc.perform(post(PATH + "/bulk")
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecordsBulk()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get(PATH +"?from=2019-05-01&to=2019-06-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workRecords", hasSize(3)));
    }

    @Test
    @WithMockOAuth2AuthenticationToken(userId = -4, authorities = {"USER"})
    public void testBulkCreate_expectBadRequest() throws Exception {
        mockMvc.perform(post(PATH + "/bulk")
                .content(JsonUtils.toJsonString(RequestCreationUtils.workRecordsBulk1()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
