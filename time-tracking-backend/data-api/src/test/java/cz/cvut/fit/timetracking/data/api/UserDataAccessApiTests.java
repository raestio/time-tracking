package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.configuration.DataAccessApiTestsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDataAccessApiTests extends DataAccessApiTestsConfiguration {

    @Autowired
    private DataAccessApi dataAccessApi;

}
