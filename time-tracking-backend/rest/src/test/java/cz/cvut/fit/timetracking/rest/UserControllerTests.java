package cz.cvut.fit.timetracking.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.timetracking.configuration.RestApiTestsConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests extends RestApiTestsConfiguration {

    private static final String PATH = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenIdIs1_shouldReturnUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(get(PATH + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ahoj")))
                .andExpect(jsonPath("$.surname", is("cau")));
    }

}
