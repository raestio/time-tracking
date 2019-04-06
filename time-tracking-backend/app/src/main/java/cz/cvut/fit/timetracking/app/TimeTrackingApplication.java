package cz.cvut.fit.timetracking.app;

import cz.cvut.fit.timetracking.configuration.TimeTrackingApplicationConfiguration;
import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import(TimeTrackingApplicationConfiguration.class)
public class TimeTrackingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackingApplication.class, args);
	}

	@Autowired
	private DataAccessApi dataAccessApi;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(dataAccessApi.findUserById(null));
    }
}
