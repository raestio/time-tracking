package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DatabaseConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfiguration.class)
public class TimeTrackingDataTests {

	@Test
	public void contextLoads() {
	}

}
