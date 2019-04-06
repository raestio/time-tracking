package cz.cvut.fit.timetracking.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataConfiguration.class)
public class DataTestsConfiguration {

    @Test
    public void contextLoads() {
    }
}
