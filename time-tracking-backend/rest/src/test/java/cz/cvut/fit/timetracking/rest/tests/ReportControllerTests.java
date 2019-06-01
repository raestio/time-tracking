package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import cz.cvut.fit.timetracking.rest.context.WithMockOAuth2AuthenticationToken;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

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
public class ReportControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/reports";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockOAuth2AuthenticationToken(authorities = {"USER", "ADMIN"})
    public void whenGetById_shouldReturnProject() throws Exception {
        mockMvc.perform(get(monthly("2019-01-01", "2019-05-01", -1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyReportItems", hasSize(2)))
                .andExpect(jsonPath("$.monthlyReportItems[0].projectReportItems", hasSize(1)))
                .andExpect(jsonPath("$.monthlyReportItems[0].projectReportItems[0].project.id", is(-1)))
                .andExpect(jsonPath("$.monthlyReportItems[0].projectReportItems[0].workReportItems", hasSize(1)))
                .andExpect(jsonPath("$.monthlyReportItems[0].projectReportItems[0].workReportItems[0].workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.monthlyReportItems[0].projectReportItems[0].workReportItems[0].minutesSpent", is(480)))
                .andExpect(jsonPath("$.monthlyReportItems[0].month", is("MARCH")))

                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[0].project.id", is(-3)))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[0].workReportItems", hasSize(1)))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[0].workReportItems[0].workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[0].workReportItems[0].minutesSpent", is(480)))

                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].project.id", is(-1)))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].workReportItems", hasSize(2)))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].workReportItems[0].workType.name", is("analyza")))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].workReportItems[0].minutesSpent", is(30)))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].workReportItems[1].workType.name", is("vyvoj")))
                .andExpect(jsonPath("$.monthlyReportItems[1].projectReportItems[1].workReportItems[1].minutesSpent", is(960)))
                .andExpect(jsonPath("$.monthlyReportItems[1].month", is("APRIL")));
    }

    private String monthly(String from, String to, int userId) {
        return PATH + "/monthly?" + from(LocalDate.parse(from)) + "&" + to(LocalDate.parse(to)) + "&" + user(userId);
    }

    private String monthly(LocalDate from, LocalDate to, int userId) {
        return PATH + "/monthly?" + from(from) + "&" + to(to) + "&" + user(userId);
    }

    private String user(int i) {
        return "userId=" + i;
    }

    private String to(LocalDate date) {
        return "to=" + dateString(date);
    }

    private String from(LocalDate date) {
        return "from=" + dateString(date);
    }

    private String dateString(LocalDate date) {
        return date.toString();
    }
}
