package cz.cvut.fit.timetracking.configuration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserTestContextConfiguration.class)
public abstract class UserTestsConfiguration {
}
