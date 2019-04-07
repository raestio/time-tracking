package cz.cvut.fit.timetracking.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "${time-tracking.configuration.basePackageName}")
@EnableWebMvc
public class RestApiTestsContextConfiguration {
}
