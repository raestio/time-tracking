package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.user.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = PackageNames.USER_CORE)
public class UserConfiguration {
}
