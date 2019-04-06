package cz.cvut.fit.timetracking.app;

import cz.cvut.fit.timetracking.configuration.TimeTrackingApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import(TimeTrackingApplicationConfiguration.class)
public class TimeTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackingApplication.class, args);
	}
}
