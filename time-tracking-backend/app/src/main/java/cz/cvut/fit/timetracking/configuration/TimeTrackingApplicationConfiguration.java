package cz.cvut.fit.timetracking.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("${time-tracking.configuration.basePackageName}")
public class TimeTrackingApplicationConfiguration {
}
