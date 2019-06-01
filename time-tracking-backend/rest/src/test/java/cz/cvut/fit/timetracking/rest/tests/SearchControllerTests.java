package cz.cvut.fit.timetracking.rest.tests;

import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Rastislav Zlacky (rastislav.zlacky@inventi.cz) on 01.06.2019.
 */
public class SearchControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/search";

    @Autowired
    private MockMvc mockMvc;


}
