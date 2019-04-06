package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataConfiguration;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataConfiguration.class)
public class TimeTrackingDataTestsConfiguration {
}
