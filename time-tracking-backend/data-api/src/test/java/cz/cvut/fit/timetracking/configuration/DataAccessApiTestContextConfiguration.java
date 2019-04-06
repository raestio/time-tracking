package cz.cvut.fit.timetracking.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "${time-tracking.configuration.basePackageName}")
public class DataAccessApiTestContextConfiguration {
}
