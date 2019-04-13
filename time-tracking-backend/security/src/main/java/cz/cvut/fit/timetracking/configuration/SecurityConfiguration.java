package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.security.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(PackageNames.SECURITY_CORE)
public class SecurityConfiguration {
}
