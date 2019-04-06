package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.rest.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = PackageNames.REST_CORE)
public class RestConfiguration {
}
